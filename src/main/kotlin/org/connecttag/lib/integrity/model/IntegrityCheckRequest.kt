package org.connecttag.lib.integrity.model

/**
 * Parameters for an integrity token request.
 *
 * @param cloudProjectNumber The Google Cloud project number linked to the app in the Google Play Console.
 * @param requestHash An optional nonce or cryptographic hash (SHA-256) of the current request/session data.
 */
data class IntegrityCheckRequest(
    val cloudProjectNumber: Long,
    val requestHash: String? = null
)
