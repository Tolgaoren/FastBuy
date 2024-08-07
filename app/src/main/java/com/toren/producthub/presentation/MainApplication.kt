package com.toren.producthub.presentation

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}