package org.connecttag.lib.integrity.play

import android.content.Context
import com.google.android.play.core.integrity.IntegrityManager
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.google.android.play.core.integrity.IntegrityTokenResponse
import com.google.android.play.core.integrity.model.IntegrityErrorCode
import kotlinx.coroutines.suspendCancellableCoroutine
import org.connecttag.lib.integrity.api.AppIntegrityEngine
import org.connecttag.lib.integrity.api.IntegrityLogger
import org.connecttag.lib.integrity.model.IntegrityCheckRequest
import org.connecttag.lib.integrity.model.IntegrityTokenResult
import kotlin.coroutines.resume

/**
 * Production implementation of [AppIntegrityEngine] wrapping Google Play Integrity API.
 */
class PlayIntegrityEngine(
    context: Context,
    private val integrityManager: IntegrityManager = IntegrityManagerFactory.create(context.applicationContext),
    private val logger: IntegrityLogger = IntegrityLogger.DEFAULT
) : AppIntegrityEngine {

    companion object {
        private const val TAG = "PlayIntegrityEngine"
    }

    override suspend fun requestIntegrityToken(request: IntegrityCheckRequest): IntegrityTokenResult {
        return suspendCancellableCoroutine { continuation ->
            val builder = IntegrityTokenRequest.builder()
                .setCloudProjectNumber(request.cloudProjectNumber)

            if (!request.requestHash.isNullOrBlank()) {
                builder.setNonce(request.requestHash)
            }

            integrityManager.requestIntegrityToken(builder.build())
                .addOnSuccessListener { response: IntegrityTokenResponse ->
                    logger.d(TAG, "Integrity token acquired successfully.")
                    if (continuation.isActive) {
                        continuation.resume(IntegrityTokenResult.Success(response.token()))
                    }
                }
                .addOnFailureListener { exception ->
                    logger.w(TAG, "Failed to request integrity token", exception)
                    val errorCode = if (exception is com.google.android.play.core.integrity.IntegrityServiceException) {
                        exception.errorCode
                    } else {
                        IntegrityErrorCode.API_NOT_AVAILABLE
                    }
                    if (continuation.isActive) {
                        continuation.resume(
                            IntegrityTokenResult.Error(
                                errorCode = errorCode,
                                errorMessage = exception.message,
                                throwable = exception
                            )
                        )
                    }
                }
        }
    }
}
