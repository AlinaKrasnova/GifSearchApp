package com.example.gifsearchapp.uiScreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

// Component for displaying an image with Glide
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GifItemGlide(url: String, modifier: Modifier = Modifier)
{
    GlideImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}
