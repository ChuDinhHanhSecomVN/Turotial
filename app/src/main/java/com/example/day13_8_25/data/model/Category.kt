package com.example.day13_8_25.data.model

import com.squareup.moshi.Json

data class Category(
    @Json(name = "id")
    private val id: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "avatar")
    val avatar: String
)
