package com.example.friendsapp.models.setting

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class SettingRepository(val settingDao: SettingDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(setting: Setting) {
        settingDao.insert(setting)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(setting: Setting) {
        settingDao.update(setting)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(setting: Setting) {
        settingDao.delete(setting)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllSettings() : LiveData<List<Setting>> {
        return settingDao.getAllSettings()
    }

}