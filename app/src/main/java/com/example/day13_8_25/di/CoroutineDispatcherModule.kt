package com.example.day13_8_25.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//Co chu module de cai DI tu biet nhay vo day ma tao object
/*
*
* cho Dagger/Hilt biết cách tạo ra các CoroutineDispatcher với qualifier tùy chỉnh.
*
* @Module → Tập hợp các “hướng dẫn” cho Hilt biết cách cung cấp object.
*
* InstallIn(SingletonComponent::class) → Module này được gắn vào SingletonComponent, nên tất cả các dependency bên trong sẽ sống suốt vòng đời của ứng dụng (singleton scope).
* */
@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {

    /*Trong module, @Provides đánh dấu hàm cung cấp dependency.
    *
    * @Provides → Hint thủ công: “Muốn CoroutineDispatcher cho IO, hãy dùng Dispatchers.IO”.

@IoDispatcher → Đây là qualifier (annotation tùy chỉnh) để phân biệt các loại dispatcher.
Vì tất cả đều trả về CoroutineDispatcher, nếu không có qualifier thì Hilt không biết lấy cái nào.

Dispatchers.IO → Dùng cho các tác vụ I/O (network, file…).
    *
    * */
    @Provides
    @IoDispatcher
    fun providerIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun providerMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DefaultDispatcher
    fun providerDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}