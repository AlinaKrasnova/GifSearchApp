package com.example.gifsearchapp.uiScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gifsearchapp.R
import com.example.gifsearchapp.viewmodels.Gif

// Screen for displaying GIF details
@Composable
fun GifDetailScreen(gif: Gif, onBack: () -> Unit)
{
    Scaffold(
        topBar =
        {
            TopAppBar(
                title = { Text("GIF Detail") },
                navigationIcon =
                {
                    IconButton(onClick = { onBack() })
                    {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    GifItemGlide(
                        url = gif.url,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Title: ${gif.title}")
                    Text(text = "Rating: ${gif.rating}")
                    Text(text = "Dimensions: ${gif.width}x${gif.height} pixels")
                    Text(text = "Size: ${gif.size} bytes")
                }
            }
        }
    )
}
