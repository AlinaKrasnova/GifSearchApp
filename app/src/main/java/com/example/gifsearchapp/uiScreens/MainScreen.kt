package com.example.gifsearchapp.uiScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gifsearchapp.R
import com.example.gifsearchapp.viewmodels.GifViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// Main screen with search bar and GIF list
@Composable
fun MainScreen(viewModel: GifViewModel = viewModel(), navController: NavHostController)
{
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }

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
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            TextField(
                value = query,
                onValueChange =
                {
                    Log.d("MainScreen", "Query changed: ${it.text}")
                    query = it
                    searchJob?.cancel()
                    searchJob = coroutineScope.launch {
                        delay(500) // Delay before searching
                        viewModel.searchGifs(it.text)
                    }
                },
                label = { Text("Search GIFs") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            GifGrid(viewModel, navController)
        }

        if (viewModel.loading.collectAsState().value && viewModel.gifs.collectAsState().value.isEmpty())
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    // Raise the load indicator higher
                    Spacer(modifier = Modifier.height(150.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}

// Grid for displaying the list of GIFs
@Composable
fun GifGrid(viewModel: GifViewModel, navController: NavHostController)
{
    val gifs by viewModel.gifs.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    if (error != null)
    {
        Log.e("GifGrid", "Error: $error")
        Text(text = "Error: $error", color = MaterialTheme.colors.error)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(gifs) { gif ->
                GifItem(gif) {
                    val encodedUrl = URLEncoder.encode(gif.url, StandardCharsets.UTF_8.toString())
                    val encodedTitle = URLEncoder.encode(gif.title, StandardCharsets.UTF_8.toString())
                    val encodedSource = URLEncoder.encode(gif.source, StandardCharsets.UTF_8.toString())
                    val encodedRating = URLEncoder.encode(gif.rating, StandardCharsets.UTF_8.toString())
                    val encodedWidth = URLEncoder.encode(gif.width, StandardCharsets.UTF_8.toString())
                    val encodedHeight = URLEncoder.encode(gif.height, StandardCharsets.UTF_8.toString())
                    val encodedSize = URLEncoder.encode(gif.size, StandardCharsets.UTF_8.toString())
                    navController.navigate("detail/$encodedUrl/$encodedTitle/$encodedSource/$encodedRating/$encodedWidth/$encodedHeight/$encodedSize")
                }
            }

            // Pagination
            item {
                if (viewModel.hasMoreResults)
                {
                    LaunchedEffect(Unit)
                    {
                        viewModel.loadMoreGifs()
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
                    {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}
