package br.com.brunocarvalhs.compracerta.commons.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.brunocarvalhs.compracerta.features.home.app.data.dataSource.GroupDao
import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.dataSource.ProductDao
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel

@Database(entities = [GroupModel::class, ProductModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopping_list_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
