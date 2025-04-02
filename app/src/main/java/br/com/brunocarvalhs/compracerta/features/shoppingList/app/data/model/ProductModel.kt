package br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = GroupModel::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["groupId"])]
)
data class ProductModel(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    @ColumnInfo(name = "name") override val name: String,
    @ColumnInfo(name = "price") override val price: Double,
    @ColumnInfo(name = "quantity") override val quantity: Int = 1,
    @ColumnInfo(name = "isChecked") override val isCompleted: Boolean = false,
    @ColumnInfo(name = "groupId") val groupId: Long
) : Product {

    override fun toCopy(
        id: Long,
        name: String,
        price: Double,
        isCompleted: Boolean,
        quantity: Int
    ): Product {
        return this.copy(
            id = id,
            name = name,
            price = price,
            isCompleted = isCompleted,
            quantity = quantity
        )
    }
}
