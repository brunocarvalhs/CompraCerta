package br.com.brunocarvalhs.compracerta

import android.app.Application
import br.com.brunocarvalhs.compracerta.commons.initialization.AppInitializationManager

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setup()
    }

    private fun setup() {
        AppInitializationManager(this.applicationContext).initialize()
    }
}