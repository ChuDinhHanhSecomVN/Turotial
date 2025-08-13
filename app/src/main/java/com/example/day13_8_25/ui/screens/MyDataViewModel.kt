package com.example.a12_8_project.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.day13_8_25.data.model.Product
import com.example.day13_8_25.data.repository.MyDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyDataViewMode (private val repository: MyDataRepository) : ViewModel() {
    private val _data = MutableLiveData<List<Product>>(emptyList())
    private val _isLoadingTakeData = mutableStateOf(false)
    private val _isLoading = mutableStateOf(false);
    val isLoading: State<Boolean> = _isLoading
    val isLoadingTakeData: State<Boolean> = _isLoadingTakeData
    val data: MutableLiveData<List<Product>> = _data

    fun saveName(data: List<Product>) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                delay(3000)
                withContext(Dispatchers.IO) {
                    for (item in data) {
                        repository.insertName(item)
                    }
                }
            } catch (e: Exception) {
                println("HANH call getProducts api loi ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllData() {
        _isLoadingTakeData.value = true
        viewModelScope.launch {
            try {
                delay(1000)
                val result = withContext(Dispatchers.IO) {
                    repository.getAllNames()
                }
                _data.value = result
            } catch (e: Exception) {
                println("HANH call getProducts api loi ${e.message}")
            } finally {
                _isLoadingTakeData.value = false
            }
        }
    }
}