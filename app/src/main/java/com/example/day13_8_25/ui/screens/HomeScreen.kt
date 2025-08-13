package com.example.day13_8_25.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.day13_8_25.ui.components.CategoryComponent
import com.example.day13_8_25.ui.components.ProductComponent
import com.example.day13_8_25.ui.components.SessionComponent
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 *Nếu bạn đang ở Composable / Screen, thì không có viewModelScope,
 *  nhưng bạn có thể dùng rememberCoroutineScope() hoặc LaunchedEffect
 *  để tạo coroutine scope gắn với vòng đời của Composable.
 *
 */
@Composable
fun HomeScreen(
    productViewModel: ProductViewModel,
    categoryViewModel: CategoryViewModel,
//    myDataViewModel: MyDataViewMode
) {
    val productList by productViewModel.productList.observeAsState(emptyList()) // Chuyển LiveData -> State
    val isLoadingProduct by productViewModel.isLoading // isLoading đã là State<Boolean> nên dùng by được


    val categoryList by categoryViewModel.categories.observeAsState(emptyList())
    val isLoadingCategory by categoryViewModel.isLoading

//    val takeDataFromDB by myDataViewModel.data.observeAsState(emptyList())
//    val isLoadingSaveData by myDataViewModel.isLoading
//    val isLoadingTakeDB by myDataViewModel.isLoadingTakeData


    val scope = rememberCoroutineScope()

    fun handleCallApi2before1() {
        scope.launch {
            val job1 = launch(start = CoroutineStart.LAZY) {
                productViewModel.getProducts()
                println("Hanh job1 finish")
            }
            val job2 = launch(start = CoroutineStart.LAZY) {
                categoryViewModel.getCategories()
                println("Hanh job2 finish")
                delay(5000)
            }

            job2.join()
            job1.start()
        }
    }


    suspend fun callApi2AfterCallAPi1() {
        supervisorScope {
            val job1 = launch {
                productViewModel.getProducts()
                println("Hanh job1 finish")
            }

            val job2 = launch(start = CoroutineStart.LAZY) {
                categoryViewModel.getCategories()
                println("Hanh job2 finish")
            }

            job1.invokeOnCompletion({ res ->
                if (res == null) {
                    job2.start()
                } else {
                    println("Hanh job1 error")
                }
            })
        }
//        scope.launch {
//            val job1 = launch {
//                productViewModel.getProducts()
//                println("Hanh job1 finish")
//            }
//
//            val job2 = launch(start = CoroutineStart.LAZY) {
//                categoryViewModel.getCategories()
//                println("Hanh job2 finish")
//            }
//
//            job1.invokeOnCompletion({ res ->
//                if (res == null) {
//                    job2.start()
//                } else {
//                    println("Hanh job1 error")
//                }
//            })
//        }
    }


    suspend fun ApiHaveException() {
        supervisorScope {
            val job1 = launch {
                println("1")
                println("2")
                println("3")
                println("4")
                categoryViewModel.getDetailCategory()
            }

            val job2 = launch() {
                categoryViewModel.getCategories()
                println("Hanh job2 finish")
            }
            if (job1 == null) {
                println("Hanh job1 finish")
            } else {
                println("Hanh job1 error")
            }
        }
    }


    fun cancelAPI() {
        scope.cancel()
    }


    fun callAndSave() {
//        myDataViewModel.saveName(productList)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        SessionComponent {
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                Button(
                    onClick = {
                        handleCallApi2before1()
                    }
                ) {
                    Text("API 1 truoc **")
                }

                Button(
                    onClick = {
                        cancelAPI()
                    }
                ) {
                    Text("Stop all")
                }
                Button(
                    onClick = {
                        productViewModel.getProducts()
                        categoryViewModel.getCategories()
                    }
                ) {
                    Text("Call API again")
                }

                Button(
                    onClick = {
                        scope.launch {
                            callApi2AfterCallAPi1()
                        }
                    }
                ) {
                    Text("API2>API1")
                }

                Button(
                    onClick = {
                        scope.launch {
                            ApiHaveException()
                        }
                    }
                ) {
                    Text("Excep api1")
                }

                Button(
                    onClick = {
                        callAndSave()
                    }
                ) {
                    Text("call and save")
                }

                Button(
                    onClick = {
//                        myDataViewModel.getAllData()
                    }
                ) {
                    Text("call Data DB")
                }

            }

//            Text("Data from DB")
//            when {
//                isLoadingTakeDB -> CircularProgressIndicator()
//                else -> LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(takeDataFromDB.size) { index ->
//                        ProductComponent(takeDataFromDB[index])
//                    }
//                }
//            }
//
//            Text("Save data")
//            when {
//                isLoadingSaveData -> CircularProgressIndicator()
//                else -> Text("Save data thanh cong")
//            }
        }

        Text("Category")
        when {
            isLoadingCategory -> CircularProgressIndicator()
            else -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categoryList.size) { index ->
                        CategoryComponent(categoryList[index])
                    }
                }
            }
        }

        Text("Product")

        when {
            isLoadingProduct -> CircularProgressIndicator()
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productList.size) { index ->
                        ProductComponent(productList[index])
                    }
                }
            }
        }
    }
}
