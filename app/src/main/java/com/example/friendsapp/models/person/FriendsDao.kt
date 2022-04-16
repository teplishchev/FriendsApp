package com.example.friendsapp.models.person

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface FriendsDao {

    @Insert(onConflict = IGNORE)
    fun insertPerson(person: Person)

    @Update
    fun updatePerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Insert(onConflict = IGNORE)
    fun insertFriendReference(reference: FriendReference)

    @Update
    fun updateFriendReference(reference: FriendReference)

    @Delete
    fun deleteFriendReference(reference: FriendReference)

    @Query("SELECT * FROM person")
    fun getAllPersons() : LiveData<List<Person>>

    @Query("SELECT * FROM person where id in (SELECT friendreference.friendId FROM person INNER JOIN friendreference on person.id == friendreference.userId WHERE id == :personId)")
    fun getPersonFriends(personId: Long) : LiveData<List<Person>>

    @Query("DELETE FROM person")
    fun deleteAllPersons()

    @Query("DELETE FROM friendreference")
    fun deleteAllFriendReferences()

}