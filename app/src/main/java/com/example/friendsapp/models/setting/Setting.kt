package com.example.friendsapp.models.setting

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setting(
    @PrimaryKey val id: Long,
    val needDownloadData: Boolean
)
