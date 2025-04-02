package br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories

import br.com.brunocarvalhs.compracerta.features.home.app.data.dataSource.GroupWithProducts
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group

interface GroupRepository {
    suspend fun getGroups(): List<Group>
    suspend fun createGroup(group: Group): Group
    suspend fun deleteGroup(group: Group): Boolean
    suspend fun getGroupById(groupId: Long  ): Group?
    suspend fun getGroupWithProducts(groupId: Long): GroupWithProducts?
}