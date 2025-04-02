package br.com.brunocarvalhs.compracerta.features.home.app.data.dataSource

import androidx.room.Embedded
import androidx.room.Relation
import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel

data class GroupWithProducts(
    @Embedded val group: GroupModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val products: List<ProductModel>
)