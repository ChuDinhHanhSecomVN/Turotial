package com.example.day13_8_25.data.remote

//
//private const val BASE_URL =
//    "https://689a9aaee727e9657f62145d.mockapi.io"
//
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(BASE_URL)
//    .build()
//
//
//object API {
//    val retrofitProductService: ProductService by lazy {
//        retrofit.create(ProductService::class.java)
//    }
//
//    val retrofitCategoryService: CategoryService by lazy {
//        retrofit.create(CategoryService::class.java)
//    }
//}