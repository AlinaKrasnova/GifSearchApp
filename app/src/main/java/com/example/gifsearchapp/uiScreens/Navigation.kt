package com.example.gifsearchapp.uiScreens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gifsearchapp.viewmodels.Gif
import com.example.gifsearchapp.viewmodels.GifViewModel
import androidx.navigation.navArgument
import androidx.navigation.NavType
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

// Navigation host to control navigation between screens
@Composable
fun Navigation(viewModel: GifViewModel, startDestination: String = "main")
{
    val navController = rememberNavController()
    NavHost(navController, startDestination)
    {
        composable("main")
        {
            MainScreen(viewModel, navController)
        }
        composable(
            "detail/{gifUrl}/{gifTitle}/{gifSource}/{gifRating}/{gifWidth}/{gifHeight}/{gifSize}",
            arguments = listOf(
                navArgument("gifUrl") { type = NavType.StringType },
                navArgument("gifTitle") { type = NavType.StringType },
                navArgument("gifSource") { type = NavType.StringType },
                navArgument("gifRating") { type = NavType.StringType },
                navArgument("gifWidth") { type = NavType.StringType },
                navArgument("gifHeight") { type = NavType.StringType },
                navArgument("gifSize") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val gifUrl = URLDecoder.decode(backStackEntry.arguments?.getString("gifUrl") ?: "", StandardCharsets.UTF_8.toString())
            val gifTitle = URLDecoder.decode(backStackEntry.arguments?.getString("gifTitle") ?: "", StandardCharsets.UTF_8.toString())
            val gifSource = URLDecoder.decode(backStackEntry.arguments?.getString("gifSource") ?: "", StandardCharsets.UTF_8.toString())
            val gifRating = URLDecoder.decode(backStackEntry.arguments?.getString("gifRating") ?: "", StandardCharsets.UTF_8.toString())
            val gifWidth = URLDecoder.decode(backStackEntry.arguments?.getString("gifWidth") ?: "", StandardCharsets.UTF_8.toString())
            val gifHeight = URLDecoder.decode(backStackEntry.arguments?.getString("gifHeight") ?: "", StandardCharsets.UTF_8.toString())
            val gifSize = URLDecoder.decode(backStackEntry.arguments?.getString("gifSize") ?: "", StandardCharsets.UTF_8.toString())
            val gif = Gif(gifUrl, gifTitle, gifSource, gifRating, gifHeight, gifWidth, gifSize)
            GifDetailScreen(gif) { navController.popBackStack() }
        }
    }
}
