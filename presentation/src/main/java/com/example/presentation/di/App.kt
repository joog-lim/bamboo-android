package com.example.presentation.di

import android.app.Application
import com.example.data.utils.DataStoreManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {



    companion object {
        private lateinit var application: App
        fun getInstance() : App = application
    }

    override fun onCreate(){
        super.onCreate()
        application = this
    }
}