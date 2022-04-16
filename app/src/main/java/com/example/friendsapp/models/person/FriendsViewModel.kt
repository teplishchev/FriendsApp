package com.example.friendsapp.models.person

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FriendsViewModel(private val repository: FriendsRepository) : ViewModel() {

    private val _mutablePersonsLive: MutableLiveData<List<Person>>? = null
    lateinit var personsLive: LiveData<List<Person>>

    suspend fun getAllPersons() : LiveData<List<Person>> {
        personsLive = repository.getAllPersons()
        return personsLive
    }

    suspend fun getPersonFriends(personId: Long) : LiveData<List<Person>> {
        return repository.getPersonFriends(personId)
    }

    suspend fun insertPerson(person: Person) {
        repository.insertPerson(person)
    }

    suspend fun insertFriendsReference(reference: FriendReference) {
        repository.insertFriendsReference(reference)
    }

    suspend fun deleteAllPersons() {
        repository.deleteAllPersons()
    }

    suspend fun deleteAllFriendsReferences() {
        repository.deleteAllFriendsReferences()
    }

}

class FriendsViewModelFactory(private val repository: FriendsRepository) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}