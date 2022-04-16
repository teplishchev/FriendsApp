package com.example.friendsapp.models.person

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity
data class Person (
    @PrimaryKey var id: Long = 0L,
    var isActive: Boolean = false,
    var age: Int = 0,
    var eyeColor: String = "",
    var name: String = "",
    var company: String = "",
    var email: String = "",
    var phone: String = "",
    var address: String = "",
    var about: String = "",
    var registered: String = "",
    var latitude: Float = 0.0F,
    var longitude: Float = 0.0F,
    var favoriteFruit: String = "",
    @Ignore
    var friends: List<IdFriends>
    ) : Parcelable {
    constructor(id: Long, isActive: Boolean, age: Int, eyeColor: String, name: String,
                company: String, email: String, phone: String, address: String, about: String,
                registered: String, latitude: Float, longitude: Float, favoriteFruit: String)
            : this(id, isActive, age, eyeColor, name, company, email, phone, address, about,
                    registered, latitude, longitude, favoriteFruit, listOf())

}