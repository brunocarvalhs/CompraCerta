package br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.repositories

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(groupId: Long): List<Product>
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
}