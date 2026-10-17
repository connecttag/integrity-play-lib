package org.connecttag.lib.integrity.api

import org.connecttag.lib.integrity.model.IntegrityCheckRequest
import org.connecttag.lib.integrity.model.IntegrityTokenResult

/**
 * Universal interface for fetching integrity tokens from Google Play Integrity API.
 */
interface AppIntegrityEngine {
    /**
     * Requests an integrity verdict token from Google Play.
     */
    suspend fun requestIntegrityToken(request: IntegrityCheckRequest): IntegrityTokenResult
}
