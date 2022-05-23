package com.citymobil.sentrylogdemo

import android.app.Application
import android.util.Log
import io.sentry.android.core.SentryAndroid
import io.sentry.android.core.SentryAndroidOptions
//import io.sentry.android.core.SentryAndroid
//import io.sentry.android.core.SentryAndroidOptions
import java.util.*

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSentry()
    }

    private fun initSentry() {
        val dsn = "https://e5813c45b60e493f87fb80ecc72fa906@o1255019.ingest.sentry.io/6423320"

        SentryAndroid.init(this) { options: SentryAndroidOptions ->
            options.isSendDefaultPii = true
            options.setDebug(false)
            options.dsn = dsn
           // options.tracesSampler = IndriverTracesSamplerCallback(sentryConfiguration)
            options.setTag("device_locale_country", Locale.getDefault().country)
            options.setTag("device_locale_language", Locale.getDefault().language)

            //roma
            options.enableAllAutoBreadcrumbs(true)
            options.isAttachStacktrace = true
            options.isAttachThreads = true
        }

        Log.d("ROMAN", "inited")
    }

}