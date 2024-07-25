package com.example.gifsearchapp.api

// Response from the Giphy API containing a list of GIF data
data class GiphyResponse(
    val data: List<GifData>
)

// GIF data retrieved from the Giphy API
data class GifData(
    val id: String,
    val title: String,
    val rating: String,
    val images: GifImages
)

// GIF image data
data class GifImages(
    val original: GifImageDetail
)

// GIF image details
data class GifImageDetail(
    val url: String,
    val width: String,
    val height: String,
    val size: String
)
