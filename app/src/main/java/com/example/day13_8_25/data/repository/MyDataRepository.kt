package com.example.day13_8_25.data.repository

import android.database.Cursor
import com.example.day13_8_25.data.local.MyDatabaseHelper
import com.example.day13_8_25.data.model.Product
import javax.inject.Inject

class MyDataRepository @Inject constructor(private val dbHelper: MyDatabaseHelper) {

    fun insertName(item: Product) {
        dbHelper.insertItem(item)
    }

    fun getAllNames(): List<Product> {
        return dbHelper.getAllData()
    }

}