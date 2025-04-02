package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product

internal sealed class ShoppingListUiState {
    data object Loading : ShoppingListUiState()
    data class Success(val items: List<Product>) : ShoppingListUiState()
    data class Error(val message: String) : ShoppingListUiState()
}