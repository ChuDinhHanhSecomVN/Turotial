package com.example.day13_8_25.data.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    val parentExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Hanh parentExceptionHandler ${throwable.message}")
    }
}