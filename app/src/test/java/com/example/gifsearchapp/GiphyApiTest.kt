import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.example.gifsearchapp.api.GiphyApi
import com.example.gifsearchapp.api.GiphyResponse
import com.example.gifsearchapp.api.GifData
import com.example.gifsearchapp.api.GifImages
import com.example.gifsearchapp.api.GifImageDetail

class GiphyApiTest
{
    private lateinit var api: GiphyApi
    private val apiKey = "iM9Zjm4porEUQsZk5mzzqdgOU7rVnfX7"

    @Before
    fun setup()
    {
        api = mock(GiphyApi::class.java)
    }

    @Test
    fun testApiReturnsCorrectGifData() = runBlocking {
        `when`(api.searchGifs(apiKey, "funny cat", 25, 0)).thenReturn(
            GiphyResponse(listOf(GifData("1", "Funny Cat", "PG", GifImages(GifImageDetail("url", "500", "500", "50000")))))
        )
        val response = api.searchGifs(apiKey, "funny cat", 25, 0)
        assertEquals("1", response.data.first().id)
        assertEquals("Funny Cat", response.data.first().title)
    }
}
