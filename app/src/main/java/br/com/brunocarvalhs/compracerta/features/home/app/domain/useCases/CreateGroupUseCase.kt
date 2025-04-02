package br.com.brunocarvalhs.compracerta.features.home.app.domain.useCases

import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group
import br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories.GroupRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreateGroupUseCase(
    private val repository: GroupRepository
) {
    suspend operator fun invoke(groupName: String): Result<Group> = runCatching {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(Date())
        val name = groupName.ifBlank { "Carrinho - $formattedDate" }
        val group = GroupModel(name = name)
        repository.createGroup(group)
    }
}