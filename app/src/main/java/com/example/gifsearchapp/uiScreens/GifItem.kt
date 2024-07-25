package com.example.gifsearchapp.uiScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gifsearchapp.viewmodels.Gif

// Element to display the GIF in the list
@Composable
fun GifItem(gif: Gif, onClick: () -> Unit)
{
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        GifItemGlide(
            url = gif.url,
            modifier = Modifier
                .size(128.dp)
                .aspectRatio(1f)
        )
    }
}