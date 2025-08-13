package com.example.day13_8_25.data.remote

import com.example.day13_8_25.data.model.Category
import retrofit2.http.GET

interface CategoryService {
    @GET("category")
    suspend fun getCategories(): List<Category>

}