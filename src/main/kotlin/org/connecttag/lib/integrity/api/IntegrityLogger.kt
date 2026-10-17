package org.connecttag.lib.integrity.api

/**
 * Pluggable logger interface for the Play Integrity library.
 */
interface IntegrityLogger {
    fun d(tag: String, message: String)
    fun w(tag: String, message: String, throwable: Throwable? = null)
    fun e(tag: String, message: String, throwable: Throwable? = null)

    companion object {
        val DEFAULT: IntegrityLogger = object : IntegrityLogger {
            override fun d(tag: String, message: String) {
                android.util.Log.d(tag, message)
            }

            override fun w(tag: String, message: String, throwable: Throwable?) {
                android.util.Log.w(tag, message, throwable)
            }

            override fun e(tag: String, message: String, throwable: Throwable?) {
                android.util.Log.e(tag, message, throwable)
            }
        }
    }
}
