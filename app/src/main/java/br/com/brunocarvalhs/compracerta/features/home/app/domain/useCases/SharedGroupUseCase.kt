package br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases

import br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories.GroupRepository

class SharedGroupUseCase(
    private val repository: GroupRepository
) {
    suspend operator fun invoke(groupId: Long): Result<String> = runCatching {
        val products = repository.getGroupWithProducts(groupId)?.products
        products?.joinToString(separator = "\n") { product ->
            "- [ ] ${product.name} - ${product.quantity} - ${product.price}"
        } ?: ""
    }
}