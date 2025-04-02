package br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model

interface Product {
    val id: Long
    val name: String
    val price: Double
    val isCompleted: Boolean
    val quantity: Int

    fun toCopy(
        id: Long = this.id,
        name: String = this.name,
        price: Double = this.price,
        isCompleted: Boolean = this.isCompleted,
        quantity: Int = this.quantity
    ) : Product
}