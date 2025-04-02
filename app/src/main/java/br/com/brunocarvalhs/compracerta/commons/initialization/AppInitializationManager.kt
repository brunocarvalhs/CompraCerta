package br.com.brunocarvalhs.compracerta.commons.initialization

import android.content.Context
import br.com.brunocarvalhs.compracerta.commons.initialization.sdks.CrashlyticsInitialization
import br.com.brunocarvalhs.compracerta.commons.initialization.sdks.RoomDatabaseInitialization
import br.com.brunocarvalhs.compracerta.commons.initialization.sdks.TimberInitialization

class AppInitializationManager(private val context: Context) {

    private val initializations: List<AppInitialization> = listOf(
        TimberInitialization(),
        CrashlyticsInitialization(context),
        RoomDatabaseInitialization(context)
    )

    fun initialize() {
        initializations.forEach { initialization ->
            initialization.start()
            initialization.execute()
            initialization.stop()
        }
    }
}