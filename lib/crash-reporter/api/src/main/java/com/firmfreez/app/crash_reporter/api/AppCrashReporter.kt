package com.firmfreez.app.crash_reporter.api

interface AppCrashReporter {
    fun setEnabled(enabled: Boolean)
    fun setUserId(id: String?)
    fun setCustomKey(name: String, value: String)
    fun log(message: String)
    fun recordException(t: Throwable)
}
