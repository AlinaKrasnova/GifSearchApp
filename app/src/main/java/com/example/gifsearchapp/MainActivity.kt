package com.example.gifsearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.gifsearchapp.api.RetrofitInstance
import com.example.gifsearchapp.repositories.GifRepository
import com.example.gifsearchapp.uiScreens.Navigation
import com.example.gifsearchapp.viewmodels.GifViewModel
import com.example.gifsearchapp.viewmodels.GifViewModelFactory

/*
The main application activity responsible for
setting the content and initialising the ViewModel
*/
class MainActivity : ComponentActivity()
{
    private lateinit var viewModel: GifViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val repository = GifRepository(RetrofitInstance.api)
        val factory = GifViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(GifViewModel::class.java)

        setContent {
            Navigation(viewModel)
        }
    }
}
