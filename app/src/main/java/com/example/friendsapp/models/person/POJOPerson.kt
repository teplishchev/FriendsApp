package com.example.friendsapp.models.person

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class POJOPerson (
    @PrimaryKey val id: Long,
    val isActive: Boolean,
    val age: Int,
    val eyeColor: String,
    val name: String,
    val company: String,
    val email: String,
    val phone: String,
    val address: String,
    val about: String,
    val registered: String,
    val latitude: Float,
    val longitude: Float,
    val favoriteFruit: String,
    val friends: List<IdFriends>
) {
    fun getPersonFromPOJO(pojo: POJOPerson) : Person {
        return Person(
            pojo.id, pojo.isActive, pojo.age, pojo.eyeColor, pojo.name, pojo.company,
            pojo.email, pojo.phone, pojo.address, pojo.about, pojo.registered, pojo.latitude,
            pojo.longitude, pojo.favoriteFruit
        )
    }
}