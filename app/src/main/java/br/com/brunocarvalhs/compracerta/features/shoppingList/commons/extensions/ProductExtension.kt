package br.com.brunocarvalhs.compracerta.features.shoppingList.commons.extensions

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product

internal fun  List<Product>.sumPrice(): Double {
    return this.sumOf { it.sumPrice() }
}

internal fun List<Product>.sumQuantity(): Int {
    return this.sumOf { it.quantity }
}

internal fun Product.sumPrice(): Double {
    return this.price * this.quantity
}

internal fun Product.isValid(): Boolean {
    return this.name.isNotEmpty() && this.price > 0 && this.quantity > 0
}