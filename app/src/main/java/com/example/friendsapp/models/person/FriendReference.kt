package com.example.friendsapp.models.person

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "friendId"])
data class FriendReference(var userId: Long = 0L, var friendId: Long = 0L)
