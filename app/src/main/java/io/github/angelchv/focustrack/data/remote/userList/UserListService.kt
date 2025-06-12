package io.github.angelchv.focustrack.data.remote.userList

import io.github.angelchv.focustrack.domain.model.UserList

interface UserListsService {
    suspend fun getLists(userId: String): List<UserList>
    suspend fun createList(userId: String, name: String): Boolean
    suspend fun getListById(userId: String, listId: String): UserList?
}
