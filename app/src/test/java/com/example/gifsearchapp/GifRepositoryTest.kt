package com.example.gifsearchapp.repositories

import com.example.gifsearchapp.api.GiphyApi
import com.example.gifsearchapp.api.GiphyResponse
import com.example.gifsearchapp.api.GifData
import com.example.gifsearchapp.api.GifImages
import com.example.gifsearchapp.api.GifImageDetail
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any

class GifRepositoryTest
{

    private val api = mock<GiphyApi>()
    private val repository = GifRepository(api)

    @Test
    fun searchGifs_returnsCorrectData() = runBlocking {
        val gifData = GifData(
            id = "1",
            title = "Test GIF",
            rating = "g",
            images = GifImages(
                original = GifImageDetail(
                    url = "https://example.com/gif1",
                    width = "100",
                    height = "100",
                    size = "1000"
                )
            )
        )

        val response = GiphyResponse(data = listOf(gifData))

        whenever(api.searchGifs(apiKey = any(), query = any(), limit = any(), offset = any())).thenReturn(response)

        val result = repository.searchGifs("test")

        assertEquals(1, result.size)
        assertEquals("https://example.com/gif1", result[0].url)
        assertEquals("Test GIF", result[0].title)
        assertEquals("g", result[0].rating)
        assertEquals("100", result[0].width)
        assertEquals("100", result[0].height)
        assertEquals("1000", result[0].size)
    }
}
