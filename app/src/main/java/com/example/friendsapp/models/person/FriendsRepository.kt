package com.example.friendsapp.models.person

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class FriendsRepository(val friendsDao: FriendsDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllPersons() : LiveData<List<Person>> {
        return friendsDao.getAllPersons()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPersonFriends(personId: Long) : LiveData<List<Person>> {
        return friendsDao.getPersonFriends(personId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPerson(person: Person) {
        friendsDao.insertPerson(person)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFriendsReference(reference: FriendReference) {
        friendsDao.insertFriendReference(reference)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllFriendsReferences() {
        friendsDao.deleteAllFriendReferences()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllPersons() {
        friendsDao.deleteAllPersons()
    }
}