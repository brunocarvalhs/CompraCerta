package br.com.brunocarvalhs.compracerta.commons.performance

import br.com.brunocarvalhs.compracerta.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.ktx.performance
import timber.log.Timber

class PerformanceManager(
    private val firebasePerformance: FirebasePerformance = Firebase.performance
) {
    fun start(name: String) {
        if (BuildConfig.DEBUG) {
            Timber.tag("Performance").d("Started trace: $name")
            return
        }
        firebasePerformance.newTrace(name).start()
    }

    fun stop(name: String) {
        if (BuildConfig.DEBUG) {
            Timber.tag("Performance").d("Stopped trace: $name")
            return
        }
        firebasePerformance.newTrace(name).stop()
    }
}