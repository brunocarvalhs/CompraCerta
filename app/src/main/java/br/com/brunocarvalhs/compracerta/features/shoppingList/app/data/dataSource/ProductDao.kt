package br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.dataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductModel)

    @Query("SELECT * FROM products WHERE groupId = :groupId")
    suspend fun getProductsByGroup(groupId: Long): List<ProductModel>

    @Delete
    suspend fun deleteProduct(product: ProductModel)

    @Update
    suspend fun updateProduct(product: ProductModel)
}
