package br.com.brunocarvalhs.compracerta.features.home.app.data.dataSource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel

@Dao
interface GroupDao {

    @Query("SELECT * FROM groups")
    suspend fun getAllGroups(): List<GroupModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: GroupModel): Long

    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupById(groupId: Long): GroupModel?

    @Delete
    suspend fun deleteGroup(group: GroupModel)

    @Transaction
    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupWithProducts(groupId: Long): GroupWithProducts?
}
