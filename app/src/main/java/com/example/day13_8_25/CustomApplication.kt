package com.example.day13_8_25

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/*
* @HiltAndroidApp báo cho Hilt tạo một Component gốc cho toàn app.
* Tạo SingletonComponent (root DI graph) và cho phép inject dependencies vào Activity, Fragment, Service, ViewModel…
*
* */
@HiltAndroidApp
class CustomApplication : Application() {

}