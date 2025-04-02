package br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases

import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group
import br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories.GroupRepository

class GetGroupListUseCase(
    private val repository: GroupRepository
) {
    suspend operator fun invoke(): Result<List<Group>> =
        runCatching {
            val list = repository.getGroups()
            list
        }
}