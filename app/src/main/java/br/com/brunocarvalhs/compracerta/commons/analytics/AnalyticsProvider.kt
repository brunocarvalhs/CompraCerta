package br.com.brunocarvalhs.compracerta.commons.analytics

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Secure
import androidx.compose.ui.Modifier
import br.com.brunocarvalhs.compracerta.BuildConfig
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class AnalyticsProvider(
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
) {

    fun track(event: AnalyticsEvents, params: Map<AnalyticsParams, String> = emptyMap()) {
        if (BuildConfig.DEBUG) {
            Timber.tag("Analytics").d("Tracked event: $event, $params")
            return
        }
        firebaseAnalytics.logEvent(event.value, Bundle().apply {
            params.forEach { (key, value) -> putString(key.value, value) }
        })
    }

    @SuppressLint("HardwareIds")
    fun setUserId(context: Context) {
        val deviceId =
            Secure.getString(context.contentResolver, Secure.ANDROID_ID)

        if (BuildConfig.DEBUG) {
            Timber.tag("Analytics").d("Set user ID: $deviceId")
            return
        }

        firebaseAnalytics.setUserId(deviceId)
    }
}
