package com.firmfreez.app.reader.impl.data.sources

import org.readium.r2.shared.publication.Publication

interface PublicationsLocalSource {

    fun put(id: String, publication: Publication)

    fun get(id: String): Publication?

    fun remove(id: String): Publication?
}
