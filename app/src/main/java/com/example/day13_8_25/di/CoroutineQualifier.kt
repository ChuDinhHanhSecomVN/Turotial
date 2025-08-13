package com.example.day13_8_25.di

import javax.inject.Qualifier

/*
*
* @Qualifier
Là annotation của Dagger/Hilt dùng để đặt tên cho dependency.

Nếu không có, Hilt sẽ bị nhầm khi có nhiều @Provides trả về cùng một kiểu dữ liệu.

@Retention(AnnotationRetention.BINARY)
BINARY → Annotation được giữ lại trong file .class và có thể dùng bởi Dagger lúc compile.

Không cần để RUNTIME vì Hilt/Dagger chỉ cần đọc thông tin lúc build để generate code.

annotation class DefaultDispatcher / IoDispatcher / MainDispatcher
Đây là cách định nghĩa qualifier custom.

Khi dùng inject, bạn phải gắn qualifier tương ứng để Hilt chọn đúng binding.
*
* */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher