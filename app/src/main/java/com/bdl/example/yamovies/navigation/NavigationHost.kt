package com.bdl.example.yamovies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bdl.example.yamovies.screens.DetailsScreen
import com.bdl.example.yamovies.screens.MovieListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Movies Screen") {
        composable("Movies Screen") {
            MovieListScreen(navController = navController)
        }
        composable(
            "Details Screen/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            DetailsScreen(id,navController = navController)
        }
    }
}





