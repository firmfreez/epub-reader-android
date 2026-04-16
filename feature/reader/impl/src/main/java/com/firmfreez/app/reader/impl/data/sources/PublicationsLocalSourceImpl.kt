package com.firmfreez.app.reader.impl.data.sources

import org.koin.core.annotation.Single
import org.readium.r2.shared.publication.Publication
import java.util.concurrent.ConcurrentHashMap

@Single(binds = [PublicationsLocalSource::class])
class PublicationsLocalSourceImpl : PublicationsLocalSource {

    private val publications = ConcurrentHashMap<String, Publication>()

    override fun put(id: String, publication: Publication) {
        publications[id] = publication
    }

    override fun get(id: String): Publication? {
        return publications[id]
    }

    override fun remove(id: String): Publication? {
        return publications.remove(id)
    }
}
