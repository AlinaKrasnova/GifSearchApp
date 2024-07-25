package com.example.gifsearchapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gifsearchapp.repositories.GifRepository

// Factory for creating an instance of GifViewModel
class GifViewModelFactory(private val repository: GifRepository) : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(GifViewModel::class.java))
        {
            return GifViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
