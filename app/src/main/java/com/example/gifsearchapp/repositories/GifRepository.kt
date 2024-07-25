package com.example.gifsearchapp.repositories

import com.example.gifsearchapp.api.GiphyApi
import com.example.gifsearchapp.viewmodels.Gif

// Repository to retrieve data from Giphy API
class GifRepository(private val api: GiphyApi) {
    private val apiKey = "iM9Zjm4porEUQsZk5mzzqdgOU7rVnfX7"

    // GIF search function on request
    suspend fun searchGifs(query: String, offset: Int = 0): List<Gif> {
        val response = api.searchGifs(apiKey, query, 25, offset)
        return response.data.map {
            Gif(
                url = it.images.original.url,
                title = it.title,
                source = "Giphy",
                rating = it.rating,
                height = it.images.original.height,
                width = it.images.original.width,
                size = it.images.original.size
            )
        }
    }
}
