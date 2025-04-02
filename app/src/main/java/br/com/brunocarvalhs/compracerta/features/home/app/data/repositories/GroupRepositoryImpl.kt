package br.com.brunocarvalhs.compracerta.features.home.app.data.repositories

import br.com.brunocarvalhs.compracerta.features.home.app.data.dataSource.GroupDao
import br.com.brunocarvalhs.compracerta.features.home.app.data.dataSource.GroupWithProducts
import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group
import br.com.brunocarvalhs.compracerta.features.home.app.domain.repositories.GroupRepository

class GroupRepositoryImpl(
    private val dao: GroupDao
) : GroupRepository {
    override suspend fun getGroups(): List<Group> {
        try {
            val list = dao.getAllGroups()
            return list
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun createGroup(group: Group): Group {
        val id = dao.insertGroup(group as GroupModel)
        dao.getGroupById(id)?.let { groupModel ->
            return groupModel
        } ?: run {
            throw Exception("Error creating group")
        }
    }

    override suspend fun deleteGroup(group: Group): Boolean {
        try {
            val groupModel = dao.getGroupById(group.id)
            if (groupModel != null) {
                dao.deleteGroup(groupModel)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override suspend fun getGroupById(groupId: Long): Group? {
        return try {
            dao.getGroupById(groupId.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getGroupWithProducts(groupId: Long): GroupWithProducts? {
        return try {
            dao.getGroupWithProducts(groupId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}