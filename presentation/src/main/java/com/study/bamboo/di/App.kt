package com.study.bamboo.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {



    companion object {
        private lateinit var application: App
    }

    override fun onCreate(){
        super.onCreate()
        application = this
    }
}