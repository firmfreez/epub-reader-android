package com.firmfreez.app.crash_reporter.impl

import com.firmfreez.app.crash_reporter.api.AppCrashReporter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.core.annotation.Single

@Single(binds = [AppCrashReporter::class])
class AppCrashReporterImpl : AppCrashReporter {

    private val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun setEnabled(enabled: Boolean) {
        crashlytics.isCrashlyticsCollectionEnabled = enabled
    }

    override fun setUserId(id: String?) {
        crashlytics.setUserId(id ?: "")
    }

    override fun setCustomKey(name: String, value: String) {
        crashlytics.setCustomKey(sanitize(name, 40), value.take(100))
    }

    override fun log(message: String) {
        crashlytics.log(message.take(500))
    }

    override fun recordException(t: Throwable) {
        crashlytics.recordException(t)
    }

    private fun sanitize(raw: String, max: Int) =
        raw.lowercase().replace(Regex("[^a-z0-9_]+"), "_").trim('_').take(max).ifEmpty { "key" }
}
