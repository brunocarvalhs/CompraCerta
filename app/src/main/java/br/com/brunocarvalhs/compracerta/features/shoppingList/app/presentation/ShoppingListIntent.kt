package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product

internal sealed class ShoppingListIntent {
    data class FetchData(val groupId: Long) : ShoppingListIntent()

    data class UpdateItem(
        val item: Product,
    ) : ShoppingListIntent()

    data class DeleteItem(
        val item: Product,
    ) : ShoppingListIntent()

    data class AddItem(
        val groupId: Long,
        val price: String,
        val quantity: Int,
    ) : ShoppingListIntent()
}