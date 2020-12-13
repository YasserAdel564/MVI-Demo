package com.mviarchitecture.app

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.blankj.utilcode.util.Utils


class App : Application() {

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)

    }
    companion object {
        lateinit var instance: App
            private set
    }
}