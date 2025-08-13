package com.example.day13_8_25.data.remote

import androidx.lifecycle.LiveData
import com.example.day13_8_25.data.model.Product
import retrofit2.http.GET

interface ProductService {
    //    Retrofit không thể trả về trực tiếp 1 list trong hàm thường mà cần suspend vì call api là bất đồng bộ
    @GET("product")
    suspend fun getProducts(): List<Product>
}