package com.bdl.example.yamovies.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bdl.example.yamovies.models.Genre
import com.bdl.example.yamovies.viewModel.GenreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerItems(onGetDetailsById: (Genre) -> Unit) {
    val genreViewModel = viewModel<GenreViewModel>()
    val state = genreViewModel.state
    when {
        state.isLoading -> {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
        }
        state.error != null -> {

        }
        else -> {

            state.genres.forEach { genre ->
                NavigationDrawerItem(
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.Transparent,
                        unselectedContainerColor = Color.Transparent,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White,
                    ),
                    label = { Text(text = "${genre.name} Movies") },
                    selected = false,
                    onClick = { onGetDetailsById(genre) }
                )
                Spacer(modifier = Modifier.height(2.dp))
                Divider()
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}
