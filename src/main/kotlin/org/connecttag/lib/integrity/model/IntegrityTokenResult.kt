package org.connecttag.lib.integrity.model

/**
 * Result of a Google Play Integrity token request.
 */
sealed interface IntegrityTokenResult {
    /**
     * Successfully retrieved an encrypted integrity token.
     * This token should be sent to your backend server to be decrypted and verified
     * via the Google Play Developer API.
     */
    data class Success(val token: String) : IntegrityTokenResult

    /**
     * Failed to retrieve an integrity token.
     */
    data class Error(val errorCode: Int, val errorMessage: String?, val throwable: Throwable? = null) : IntegrityTokenResult
}
