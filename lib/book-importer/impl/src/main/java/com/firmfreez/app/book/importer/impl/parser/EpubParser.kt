package com.firmfreez.app.book.importer.impl.parser

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.zip.ZipInputStream
import javax.xml.parsers.DocumentBuilderFactory

internal class EpubParser {

    fun parse(inputStream: InputStream): ParsedEpub? {
        val entries = readZipEntries(inputStream)

        val mimetype = entries["mimetype"]?.decodeToString()?.trim()
        val containerXml = entries["META-INF/container.xml"]?.decodeToString()

        if (mimetype != EPUB_MIME_TYPE || containerXml.isNullOrBlank()) {
            return null
        }

        val opfPath = parseRootFilePath(containerXml) ?: return null
        val opfXml = entries[opfPath]?.decodeToString() ?: return null
        val document = parseXml(opfXml) ?: return null

        return ParsedEpub(
            title = document.firstTextByTagNames("dc:title", "title")?.normalizeMetadata(),
            author = document.firstTextByTagNames("dc:creator", "creator")?.normalizeMetadata(),
            description = document.firstTextByTagNames("dc:description", "description")?.normalizeMetadata(),
            coverPathInZip = document.findCoverPath(opfPath = opfPath, entries = entries),
            entries = entries,
        )
    }

    private fun readZipEntries(input: InputStream): Map<String, ByteArray> {
        val result = linkedMapOf<String, ByteArray>()

        ZipInputStream(input).use { zip ->
            while (true) {
                val entry = zip.nextEntry ?: break
                if (!entry.isDirectory) {
                    result[entry.name] = zip.readBytes()
                }
                zip.closeEntry()
            }
        }

        return result
    }

    private fun parseRootFilePath(containerXml: String): String? {
        val document = parseXml(containerXml) ?: return null
        val rootFiles = document.getElementsByTagName("rootfile")
        if (rootFiles.length == 0) return null

        val rootFile = rootFiles.item(0) as? Element ?: return null
        return rootFile.getAttribute("full-path").takeIf { it.isNotBlank() }
    }

    private fun parseXml(xml: String): Document? = runCatching {
        val factory = DocumentBuilderFactory.newInstance().apply {
            isNamespaceAware = false
        }
        val builder = factory.newDocumentBuilder()
        builder.parse(ByteArrayInputStream(xml.toByteArray()))
    }.getOrNull()

    private fun Document.firstTextByTagNames(vararg names: String): String? {
        for (name in names) {
            val nodeList = getElementsByTagName(name)
            if (nodeList.length > 0) {
                val value = nodeList.item(0)?.textContent?.trim()
                if (!value.isNullOrBlank()) return value
            }
        }
        return null
    }

    private fun org.w3c.dom.Document.findCoverPath(
        opfPath: String,
        entries: Map<String, ByteArray>,
    ): String? {
        data class ManifestItem(
            val id: String,
            val href: String,
            val mediaType: String,
            val properties: String,
        )

        val manifestItems = buildList {
            val manifestNodes = getElementsByTagName("item")
            for (i in 0 until manifestNodes.length) {
                val item = manifestNodes.item(i) as? Element ?: continue
                add(
                    ManifestItem(
                        id = item.getAttribute("id"),
                        href = item.getAttribute("href"),
                        mediaType = item.getAttribute("media-type"),
                        properties = item.getAttribute("properties"),
                    )
                )
            }
        }

        // 1. EPUB2: <meta name="cover" content="cover-image-id" />
        val metaNodes = getElementsByTagName("meta")
        var coverId: String? = null
        for (i in 0 until metaNodes.length) {
            val node = metaNodes.item(i) as? Element ?: continue
            val name = node.getAttribute("name")
            val content = node.getAttribute("content")

            if (name.equals("cover", ignoreCase = true) && content.isNotBlank()) {
                coverId = content
                break
            }
        }

        manifestItems.firstOrNull { it.id == coverId && it.href.isNotBlank() }?.let { item ->
            val resolved = resolveRelativePath(
                baseFilePath = opfPath,
                relativePath = item.href,
            )
            if (entries.containsKey(resolved)) return resolved
        }

        // 2. EPUB3: properties="cover-image"
        manifestItems.firstOrNull { item ->
            item.properties
                .split(" ")
                .any { it == "cover-image" } && item.href.isNotBlank()
        }?.let { item ->
            val resolved = resolveRelativePath(
                baseFilePath = opfPath,
                relativePath = item.href,
            )
            if (entries.containsKey(resolved)) return resolved
        }

        // 3. guide/reference type="cover"
        val referenceNodes = getElementsByTagName("reference")
        for (i in 0 until referenceNodes.length) {
            val node = referenceNodes.item(i) as? Element ?: continue
            val type = node.getAttribute("type")
            val href = node.getAttribute("href")

            if (type.equals("cover", ignoreCase = true) && href.isNotBlank()) {
                val resolved = resolveRelativePath(
                    baseFilePath = opfPath,
                    relativePath = href.substringBefore('#'),
                )
                if (entries.containsKey(resolved)) return resolved
            }
        }

        // 4. Эвристика: файл, похожий на обложку
        manifestItems.firstOrNull { item ->
            val href = item.href.lowercase()
            val mediaType = item.mediaType.lowercase()

            mediaType.startsWith("image/") && (
                    href.endsWith("/cover.jpg") ||
                            href.endsWith("/cover.jpeg") ||
                            href.endsWith("/cover.png") ||
                            href.endsWith("/cover.webp") ||
                            href.contains("cover")
                    )
        }?.let { item ->
            val resolved = resolveRelativePath(
                baseFilePath = opfPath,
                relativePath = item.href,
            )
            if (entries.containsKey(resolved)) return resolved
        }

        // 5. Первая картинка из manifest
        manifestItems.firstOrNull { item ->
            item.mediaType.lowercase().startsWith("image/") && item.href.isNotBlank()
        }?.let { item ->
            val resolved = resolveRelativePath(
                baseFilePath = opfPath,
                relativePath = item.href,
            )
            if (entries.containsKey(resolved)) return resolved
        }

        // 6. Последний fallback: картинка с именем cover прямо в zip
        entries.keys.firstOrNull { path ->
            val lower = path.lowercase()
            (lower.endsWith(".jpg") ||
                    lower.endsWith(".jpeg") ||
                    lower.endsWith(".png") ||
                    lower.endsWith(".webp")) &&
                    lower.contains("cover")
        }?.let { return it }

        // 7. Совсем fallback: первая картинка в zip
        entries.keys.firstOrNull { path ->
            val lower = path.lowercase()
            lower.endsWith(".jpg") ||
                    lower.endsWith(".jpeg") ||
                    lower.endsWith(".png") ||
                    lower.endsWith(".webp")
        }?.let { return it }

        return null
    }

    private fun resolveRelativePath(
        baseFilePath: String,
        relativePath: String,
    ): String {
        val baseDir = baseFilePath.substringBeforeLast('/', missingDelimiterValue = "")
        return if (baseDir.isBlank()) relativePath else "$baseDir/$relativePath"
    }

    private fun String.normalizeMetadata(): String? {
        return trim()
            .replace(Regex("\\s+"), " ")
            .takeIf { it.isNotBlank() }
    }

    private companion object {
        const val EPUB_MIME_TYPE = "application/epub+zip"
    }
}