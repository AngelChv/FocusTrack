package io.github.angelchv.focustrack.data.repository

import io.github.angelchv.focustrack.data.remote.userList.UserListsService
import io.github.angelchv.focustrack.domain.model.UserList
import javax.inject.Inject

class UserListsRepository @Inject constructor(
    private val listsService: UserListsService,
) {
    suspend fun getUserLists(userId: String): List<UserList> = listsService.getLists(userId)

    suspend fun getListById(userId: String, listId: String): UserList? =
        listsService.getListById(userId, listId)

    suspend fun addList(userId: String, name: String): Boolean =
        listsService.createList(userId, name)

    suspend fun addMovieToList(userId: String, listId: String, movieId: Int): Boolean {
        return listsService.addMovieToList(userId, listId, movieId)
    }

    suspend fun removeMoveFromList(userId: String, listId: String, movieId: Int): Boolean {
        return listsService.removeMovieFromList(userId, listId, movieId)

    }

    suspend fun deleteListById(userId: String, listId: String): Boolean {
        return listsService.deleteListById(userId, listId)
    }
}