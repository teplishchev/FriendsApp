package com.example.friendsapp.models

import android.content.Context
import com.example.friendsapp.MainActivity
import com.example.friendsapp.fragments.AllPersonsFragment
import com.example.friendsapp.fragments.PersonDetailsFragment
import com.example.friendsapp.models.person.FriendsDatabase
import com.example.friendsapp.models.person.FriendsRepository
import com.example.friendsapp.models.person.FriendsViewModel
import com.example.friendsapp.models.person.FriendsViewModelFactory
import com.example.friendsapp.models.setting.SettingRepository
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class, ModelsModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(fragment: AllPersonsFragment)
    fun inject(fragment: PersonDetailsFragment)

}

@Module
class ModelsModule {

    @Provides
    fun provideDatabase(context: Context) : FriendsDatabase {
        return FriendsDatabase.getDatabase(context)
    }

    @Provides
    fun providePersonRepository(db: FriendsDatabase) : FriendsRepository {
        return FriendsRepository(db.friendsDao())
    }

    @Provides
    fun provideSettingRepository(db: FriendsDatabase) : SettingRepository {
        return SettingRepository(db.settingDao())
    }

}

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext() : Context {
        return context
    }

    @Provides
    fun provideFriendsViewModelFactory(repository: FriendsRepository) : FriendsViewModelFactory {
        return FriendsViewModelFactory(repository)
    }

}