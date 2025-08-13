package com.example.day13_8_25.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.day13_8_25.data.base.BaseViewModel
import com.example.day13_8_25.data.model.Product
import com.example.day13_8_25.data.repository.ProductRepository
import com.example.day13_8_25.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
*
* @HiltViewModel → Hilt sẽ quản lý vòng đời ViewModel và có thể inject dependencies.

@Inject constructor → Báo cho Hilt “đây là cách tạo ProductViewModel — cần ProductRepository và một CoroutineDispatcher có qualifier @IoDispatcher”.
*
* */
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :
    BaseViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _productList = MutableLiveData<List<Product>>(emptyList())

    // The external immutable LiveData for the request status
    val productList: LiveData<List<Product>> = _productList

    private val _isLoading = mutableStateOf(false);
    val isLoading: State<Boolean> = _isLoading


    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
//    init {
//        getMarsPhotos()
//    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    fun getProducts() {
        viewModelScope.launch {
            println("Hanh getProducts thread ${Thread.currentThread().name}}")
            _isLoading.value = true
            try {
                val listResult = withContext(ioDispatcher) {
                    println("Hanh getProducts thread ${Thread.currentThread().name}}")
                    repository.fetchProducts()
                }
                _productList.value = listResult

            } catch (e: Exception) {
                _productList.value = emptyList()
                println("HANH call getProducts api loi ${e.message}")
            } finally {
                _isLoading.value = false
            }
            println("Hanh getProducts thread ${Thread.currentThread().name}}")
        }
    }

}