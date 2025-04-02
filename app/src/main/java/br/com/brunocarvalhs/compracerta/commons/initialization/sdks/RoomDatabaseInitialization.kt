package br.com.brunocarvalhs.compracerta.commons.initialization.sdks

import android.content.Context
import br.com.brunocarvalhs.compracerta.commons.database.AppDatabase
import br.com.brunocarvalhs.compracerta.commons.initialization.AppInitialization
import timber.log.Timber

class RoomDatabaseInitialization(private val context: Context) : AppInitialization() {

    override fun execute() {
         val database = AppDatabase.getInstance(context)
         Timber.d("Room database initialized: $database")
    }

    override fun tag(): String {
        return "RoomDatabaseInitialization"
    }
}