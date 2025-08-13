package com.example.day13_8_25.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.day13_8_25.data.base.BaseViewModel
import com.example.day13_8_25.data.model.Category
import com.example.day13_8_25.data.repository.CategoryRepository
import com.example.day13_8_25.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


//@Inject constructor() có nghĩa là sẽ khởi tạo CategoryViewModel dựa trên 2 phụ thuộc là CategoryRepository và CoroutineDispatcher
//Dagger sẽ tự động resolve dependency graph.
//@HiltViewModel giúp Hilt biết cần tạo ViewModel và inject dependencies vào.
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    private val _categories = MutableLiveData<List<Category>>(emptyList())
    var categories: LiveData<List<Category>> = _categories
    private val _isLoading = mutableStateOf(false)
    var isLoading: State<Boolean> = _isLoading

    fun getCategories() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
//                1
                /*
                * tại sao phải dùng withContext? tất nhiên là là hàm chạy tuần tự ~ suspen nó sẽ chờ xong nó mới chạy tiếp
                * còn launch thì tạo ra coroutine khác và chạy xong xong thì nó vẫn chạy tiếp không chờ
                * */
                withContext(ioDispatcher) {
                    println("Hanh getCategories thread 2 ${Thread.currentThread().name}}")
                    val listResult = repository.fetchCategories()
                    _categories.postValue(listResult)
                }
                /*
                * Hai dòng này đang ghi dữ liệu lên LiveData và Compose state từ luồng IO thread,
                * mà LiveData .value yêu cầu gọi trên main thread → sẽ crash ngay với lỗi kiểu:
                *
                *
                * Nếu muốn update từ background thread mà không crash, LiveData còn có .postValue()
                * (nó tự queue về Main thread), nhưng Compose state thì không có postValue → bắt buộc về Main.
                *
                * */
            } catch (e: Exception) {
                _categories.value = emptyList()
                println("HANH call getCategories api loi ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getDetailCategory() {
        viewModelScope.launch(parentExceptionHandler) {
            throw Exception()
        }
    }
}
