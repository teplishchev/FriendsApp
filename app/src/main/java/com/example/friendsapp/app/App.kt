package com.example.friendsapp.app

import android.app.Application
import com.example.friendsapp.models.AppComponent
import com.example.friendsapp.models.AppModule
import com.example.friendsapp.models.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                        .builder()
                        .appModule(AppModule(context = this))
                        .build()
    }

}