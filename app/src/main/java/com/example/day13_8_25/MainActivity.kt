package com.example.day13_8_25

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.day13_8_25.ui.screens.CategoryViewModel
import com.example.day13_8_25.ui.screens.HomeScreen
import com.example.day13_8_25.ui.screens.ProductViewModel
import com.example.day13_8_25.ui.theme.Day13_8_25Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

//@AndroidEntryPoint báo Hilt tạo một subcomponent cho Activity.
//
//by viewModels() lấy ViewModel đã được Hilt inject dependencies sẵn.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /*
    *
    * by viewModels(): Delegate của AndroidX để tạo hoặc lấy lại ProductViewModel gắn với vòng đời Activity.
Nếu ViewModel có @HiltViewModel và constructor @Inject, Hilt sẽ tự inject các dependency cần thiết.
    * */

    private val productViewModel: ProductViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Day13_8_25Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(productViewModel, categoryViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Day13_8_25Theme {
        Greeting("Android")
    }
}