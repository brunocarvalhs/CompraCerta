package br.com.brunocarvalhs.compracerta.commons.analytics

import com.google.firebase.analytics.FirebaseAnalytics

enum class AnalyticsEvents(val value: String) {
    // Eventos padrão do GA4
    VISUALIZATION(FirebaseAnalytics.Event.SCREEN_VIEW),
    APP_OPEN(FirebaseAnalytics.Event.APP_OPEN),
    SHARE(FirebaseAnalytics.Event.SHARE),
    CLICK(FirebaseAnalytics.Event.SELECT_CONTENT),

    // Eventos personalizados específicos do aplicativo
    SUBMIT_ERROR_REPORT("submit_error_report"),
    LONG_CLICK("long_click"),
}