package org.connecttag.lib.integrity

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.connecttag.lib.integrity.fake.FakeIntegrityEngine
import org.connecttag.lib.integrity.model.IntegrityCheckRequest
import org.connecttag.lib.integrity.model.IntegrityTokenResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeIntegrityEngineTest {

    private lateinit var integrityEngine: FakeIntegrityEngine

    @Before
    fun setUp() {
        integrityEngine = FakeIntegrityEngine()
    }

    @Test
    fun requestIntegrityToken_returnsConfiguredToken() = runTest {
        val request = IntegrityCheckRequest(
            cloudProjectNumber = 1234567890L,
            requestHash = "test_hash"
        )

        val result = integrityEngine.requestIntegrityToken(request)

        assertTrue(result is IntegrityTokenResult.Success)
        val success = result as IntegrityTokenResult.Success
        assertEquals("fake_integrity_token_for_testing", success.token)
        assertEquals(request, integrityEngine.lastRequest)
        assertEquals(1, integrityEngine.requestCount)
    }

    @Test
    fun requestIntegrityToken_whenSimulatingError_returnsErrorResult() = runTest {
        integrityEngine.simulatedResult = IntegrityTokenResult.Error(
            errorCode = -1,
            errorMessage = "Integrity check failed"
        )

        val result = integrityEngine.requestIntegrityToken(IntegrityCheckRequest(100L))

        assertTrue(result is IntegrityTokenResult.Error)
        val error = result as IntegrityTokenResult.Error
        assertEquals(-1, error.errorCode)
        assertEquals("Integrity check failed", error.errorMessage)
    }
}
