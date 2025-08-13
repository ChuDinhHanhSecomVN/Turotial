package com.example.day13_8_25.di

import com.example.day13_8_25.commom.Config
import com.example.day13_8_25.data.remote.CategoryService
import com.example.day13_8_25.data.remote.ProductService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/*
*
* @Module: Nơi bạn định nghĩa cách tạo ra các dependency.

Bạn dùng module khi không thể hoặc không muốn dùng @Inject constructor trực tiếp.
*
@InstallIn(SingletonComponent::class) → Module này được “gắn” vào SingletonComponent, nghĩa là tất cả dependency bên trong sẽ sống suốt vòng đời app (singleton).
* */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*
    *
    * @Provides → Đây là một “hint” thủ công: “Muốn tạo Moshi thì gọi hàm này”.

@Singleton → Moshi chỉ được tạo một lần duy nhất trong suốt vòng đời app.
    * */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Config.BASE_URL)
            .build()

    }

    @Provides
    @Singleton
    fun provideRetrofitProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitCategoryService(retrofit: Retrofit): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }
}