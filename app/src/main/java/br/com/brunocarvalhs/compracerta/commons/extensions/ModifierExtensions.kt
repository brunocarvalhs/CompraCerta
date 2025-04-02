package br.com.brunocarvalhs.compracerta.commons.extensions

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsEvents
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsParams
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsProvider
import timber.log.Timber

private val analyticsProvider = AnalyticsProvider()

@Composable
fun Modifier.trackLayout(
    params: Map<AnalyticsParams, String> = emptyMap()
): Modifier {
    LaunchedEffect(Unit) {
        analyticsProvider.track(AnalyticsEvents.VISUALIZATION, params)
        Timber.d("Tracked on open layout event: ${AnalyticsEvents.VISUALIZATION}, params: $params")
    }

    return this
}


fun Modifier.trackClick(
    params: Map<AnalyticsParams, String> = emptyMap(),
): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                analyticsProvider.track(AnalyticsEvents.CLICK, params)
                Timber.d("Tracked click event: ${AnalyticsEvents.CLICK}, $params")
            }
        )
    }
}

fun Modifier.trackLongClick(
    params: Map<AnalyticsParams, String> = emptyMap(),
): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(
            onLongPress = {
                analyticsProvider.track(AnalyticsEvents.LONG_CLICK, params)
                Timber.d("Tracked long click event: ${AnalyticsEvents.LONG_CLICK}, $params")
            }
        )
    }
}

fun (() -> Unit).trackClick(
    params: Map<AnalyticsParams, String> = emptyMap(),
): () -> Unit {
    return {
        analyticsProvider.track(AnalyticsEvents.CLICK, params)
        Timber.d("Tracked click event: ${AnalyticsEvents.CLICK}, params: $params")
        this() // Executa a ação original
    }
}

fun (() -> Unit).trackLongClick(
    params: Map<AnalyticsParams, String> = emptyMap(),
): () -> Unit {
    return {
        analyticsProvider.track(AnalyticsEvents.LONG_CLICK, params)
        Timber.d("Tracked long click event: ${AnalyticsEvents.LONG_CLICK}, $params")
        this() // Executa a ação original
    }
}

fun Unit.trackClick(
    params: Map<AnalyticsParams, String> = emptyMap(),
): Unit {
    analyticsProvider.track(AnalyticsEvents.CLICK, params)
    Timber.d("Tracked click event: ${AnalyticsEvents.CLICK}, $params")
}
