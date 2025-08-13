package com.example.day13_8_25.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.day13_8_25.data.model.Product

class MyDatabaseHelper(context: Context) :

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "MyTable"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"

        private const val COLUMN_AVATAR = "avatar"

        private const val COLUMN_CREATED_AT = "createdAt"
        private const val CATEGORY_ID = "categoryId"
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertItem(item: Product): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, item.name)
            put(COLUMN_AVATAR, item.avatar)
            put(COLUMN_CREATED_AT, item.createdAt)
            put(CATEGORY_ID, item.categoryId)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    fun getAllData(): List<Product> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val products = mutableListOf<Product>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val avatar = cursor.getString(cursor.getColumnIndexOrThrow("avatar"))
                val createdAt = cursor.getString(cursor.getColumnIndexOrThrow("createdAt"))
                val categoryId = cursor.getString(cursor.getColumnIndexOrThrow("categoryId"))
                products.add(Product(id, createdAt, name, avatar, categoryId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return products
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_AVATAR TEXT, $COLUMN_CREATED_AT TEXT,$CATEGORY_ID TEXT)"
        db?.execSQL(CREATE_TABLE)
    }
}