package com.example.friendsapp.models.person

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.friendsapp.models.setting.Setting
import com.example.friendsapp.models.setting.SettingDao

@Database(entities = [
    Person::class,
    FriendReference::class,
    Setting::class],
    version = 2)
abstract class FriendsDatabase : RoomDatabase() {

    abstract fun friendsDao() : FriendsDao
    abstract fun settingDao() : SettingDao

    companion object {
        private const val DB_NAME = "FriendsApp.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `setting` (`id` INTEGER NOT NULL, " +
                        "`needDownloadData` INTEGER NOT NULL, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        @Volatile
        private var INSTANCE: FriendsDatabase? = null

        fun getDatabase(context: Context): FriendsDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FriendsDatabase::class.java,
                    DB_NAME
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance

                instance
            }
        }
    }
}