package com.example.gifsearchapp.viewmodels

import android.os.Parcelable
import android.util.Log
import kotlinx.parcelize.Parcelize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifsearchapp.repositories.GifRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// GIF data implementing Parcelable
@Parcelize
data class Gif(
    val url: String,
    val title: String,
    val source: String,
    val rating: String,
    val height: String,
    val width: String,
    val size: String
) : Parcelable

// ViewModel for managing application state and logic
class GifViewModel(private val repository: GifRepository) : ViewModel()
{
    private val _gifs = MutableStateFlow<List<Gif>>(emptyList())
    val gifs: StateFlow<List<Gif>> = _gifs

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentQuery: String = ""
    private var currentOffset: Int = 0
    private var isLastPage: Boolean = false

    val hasMoreResults: Boolean
        get() = !isLastPage && !loading.value

    // GIF search function by request
    fun searchGifs(query: String)
    {
        currentQuery = query
        currentOffset = 0
        isLastPage = false
        _gifs.value = emptyList()
        loadMoreGifs()
    }

    // Function for loading additional GIFs for pagination
    fun loadMoreGifs()
    {
        if (_loading.value || isLastPage) return

        viewModelScope.launch {
            _loading.value = true
            try
            {
                Log.d("GifViewModel", "Searching for gifs with query: $currentQuery")
                val results = repository.searchGifs(currentQuery, currentOffset)
                if (results.isEmpty())
                {
                    isLastPage = true
                } else
                {
                    _gifs.value = _gifs.value + results
                    currentOffset += results.size
                }
                Log.d("GifViewModel", "Search results: ${results.size} gifs found")
            } catch (e: Exception) {
                Log.e("GifViewModel", "Error searching for gifs", e)
                _error.value = e.message
            } finally
            {
                _loading.value = false
            }
        }
    }
}