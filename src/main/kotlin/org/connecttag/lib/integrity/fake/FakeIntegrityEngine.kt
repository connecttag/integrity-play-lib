package org.connecttag.lib.integrity.fake

import org.connecttag.lib.integrity.api.AppIntegrityEngine
import org.connecttag.lib.integrity.model.IntegrityCheckRequest
import org.connecttag.lib.integrity.model.IntegrityTokenResult

/**
 * Mock implementation of [AppIntegrityEngine] for local tests and developer mock mode.
 */
class FakeIntegrityEngine(
    var simulatedResult: IntegrityTokenResult = IntegrityTokenResult.Success("fake_integrity_token_for_testing")
) : AppIntegrityEngine {

    var lastRequest: IntegrityCheckRequest? = null
    var requestCount: Int = 0

    override suspend fun requestIntegrityToken(request: IntegrityCheckRequest): IntegrityTokenResult {
        lastRequest = request
        requestCount++
        return simulatedResult
    }
}
