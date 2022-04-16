package com.example.friendsapp.models.setting

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SettingDao {

    @Insert
    fun insert(setting: Setting)

    @Update
    fun update(setting: Setting)

    @Delete
    fun delete(setting: Setting)

    @Query("SELECT * FROM setting")
    fun getAllSettings() : LiveData<List<Setting>>

}