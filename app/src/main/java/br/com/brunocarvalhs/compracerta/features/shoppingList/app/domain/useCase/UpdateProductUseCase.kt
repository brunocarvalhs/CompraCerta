package br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.useCase

import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.repositories.ProductRepository

class UpdateProductUseCase(
    private val repository: ProductRepository
) {

    suspend fun invoke(product: Product): Result<Unit> = kotlin.runCatching {
        repository.updateProduct(product)
    }
}