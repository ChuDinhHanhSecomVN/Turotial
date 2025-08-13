package com.example.day13_8_25.data.repository

import com.example.day13_8_25.data.model.Category
import com.example.day13_8_25.data.remote.CategoryService
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: CategoryService
) {
    suspend fun fetchCategories(): List<Category> {
        return api.getCategories()
    }

    fun saveToDB() {
        //save to db

    }
}