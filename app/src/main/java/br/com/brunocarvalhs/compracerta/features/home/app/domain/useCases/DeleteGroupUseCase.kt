package br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases

import br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories.GroupRepository

class DeleteGroupUseCase(
    private val repository: GroupRepository
) {
    suspend operator fun invoke(groupId: Long): Result<Boolean> = runCatching {
        val group = repository.getGroupById(groupId)
        if (group != null) {
            repository.deleteGroup(group)
        } else {
            false
        }
    }
}