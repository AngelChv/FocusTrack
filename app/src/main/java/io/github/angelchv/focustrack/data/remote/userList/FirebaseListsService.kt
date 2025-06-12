package io.github.angelchv.focustrack.data.remote.userList

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import io.github.angelchv.focustrack.domain.dto.CreateUserListDto
import io.github.angelchv.focustrack.domain.model.UserList
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseListsService @Inject constructor() : UserListsService {
    val firestore = FirebaseFirestore.getInstance()

    override suspend fun getLists(userId: String): List<UserList> {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("lists")
                .get()
                .await()
                .map { it.toObject(UserList::class.java).copy(id = it.id) }
        } catch (e: Exception) {
            Log.e("FirebaseListsService", "Error getting lists", e)
            emptyList()
        }
    }

    override suspend fun createList(userId: String, name: String): Boolean {
        return try {
            val list = CreateUserListDto(name = name)
            firestore.collection("users")
                .document(userId)
                .collection("lists")
                .add(list)
                .await()
            true
        } catch (e: Exception) {
            Log.e("FirebaseListsService", "Error creating list", e)
            false
        }
    }

    override suspend fun getListById(userId: String, listId: String): UserList? {
        return try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("lists")
                .document(listId)
                .get()
                .await()

            if (snapshot.exists()) {
                snapshot.toObject(UserList::class.java)?.copy(id = snapshot.id)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("FirebaseListsService", "Error getting list", e)
            null
        }
    }

    override suspend fun addMovieToList(
        userId: String,
        listId: String,
        movieId: Int,
    ): Boolean {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("lists")
                .document(listId)
                .update("movieIds", FieldValue.arrayUnion(movieId))
                .await()
            true
        } catch (e: Exception) {
            Log.e("FirebaseListsService", "Error adding movie to list", e)
            false
        }
    }

    override suspend fun removeMovieFromList(
        userId: String,
        listId: String,
        movieId: Int,
    ): Boolean {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("lists")
                .document(listId)
                .update("movieIds", FieldValue.arrayRemove(movieId))
                .await()
            true
        } catch (e: Exception) {
            Log.e("FirebaseListsService", "Error removing movie from list", e)
            false
        }
    }

    override suspend fun deleteListById(
        userId: String,
        listId: String,
    ): Boolean {
        return try {
            firestore.collection("users")
                .document(userId)
                .collection("lists")
                .document(listId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            Log.e("FirebaseListsService", "Error deleting list", e)
            false
        }
    }
}