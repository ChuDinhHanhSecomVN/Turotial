package com.example.day13_8_25.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.day13_8_25.data.model.Product
import coil.size.Size
import com.example.day13_8_25.R

@Composable
fun ProductComponent(item: Product) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .shadow(elevation = 8.dp)
            .size(height = 200.dp, width = 150.dp)
            .background(color = colorResource(R.color.teal_700))

    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.avatar)
                .size(Size.ORIGINAL) // Optional: specify size
                .build(),
            contentDescription = item.name
        )
        Text(text = item.name)
    }
}
