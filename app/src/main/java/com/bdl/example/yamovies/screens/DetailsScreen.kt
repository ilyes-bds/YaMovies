package com.bdl.example.yamovies.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bdl.example.yamovies.R
import com.bdl.example.yamovies.models.MovieDetailList
import com.bdl.example.yamovies.utils.Util.formatRuntime
import com.bdl.example.yamovies.viewModel.MovieDetailViewModel
import com.bdl.example.yamovies.viewModel.MovieDetailViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(movieId: Int, navController: NavHostController) {
    val viewModelFactory = MovieDetailViewModelFactory(movieId)
    val viewModel: MovieDetailViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(Color.Transparent),
                title = {
                    if (state.isLoading) {
                        Text(text = "Movie Details")
                    } else {
                        Text(text = "${state.detailsMovie.title}")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(paddingValues)
            ) {
                if (state.isLoading) {
                    SkeletonDetailsScreen()
                } else {
                    val details = state.detailsMovie

                    BackGroundPoster(movieDetails = details)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ForegroundPoster(movieDetails = details)

                        Column(
                            Modifier
                                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp, top = 16.dp)
                                .verticalScroll(rememberScrollState())
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Spacer(modifier = Modifier.height(25.dp))

                            details.title.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    lineHeight = 40.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Rating(movieDetails = details, modifier = Modifier)

                            TextBuilder(
                                icon = Icons.Filled.Info,
                                title = "Summary:",
                                bodyText = details.overview ?: "Unknown"
                            )
                            DetailsRow(
                                title = "Release Date:",
                                value = details.releaseDate ?: "Unknown"
                            )
                            DetailsRow(
                                title = "Runtime:",
                                value = formatRuntime(details.runtime)
                            )
                            DetailsRow(
                                title = "Genres:",
                                value = details.genres.joinToString { it.name }
                            )
                            DetailsRow(
                                title = "Production Companies:",
                                value = details.productionCompanies.joinToString { it.name }
                            )
                            DetailsRow(
                                title = "Spoken Languages:",
                                value = details.spokenLanguages.joinToString { it.name }
                            )

                            ImageRow(movieDetails = details)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailsRow(title: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

@Composable
fun ImageRow(movieDetails: MovieDetailList) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500/${movieDetails.backdropPath}",
        contentDescription = "",
        Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun TextBuilder(icon: ImageVector, title: String, bodyText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Person",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
    Text(text = bodyText, color = Color.White)
}

@SuppressLint("DefaultLocale")
@Composable
fun Rating(movieDetails: MovieDetailList, modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Star, contentDescription = "", tint = Color.Yellow)
        Text(
            text = String.format("%.2f", movieDetails.voteAverage),
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = modifier.width(25.dp))
        Icon(
            painter = painterResource(id = R.drawable.time_24),
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = "${movieDetails.runtime}",
            modifier.padding(start = 6.dp),
            color = Color.White
        )
        Spacer(modifier = modifier.width(25.dp))
        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "", tint = Color.White)
        movieDetails.releaseDate?.let {
            Text(
                text = it,
                modifier.padding(start = 6.dp),
                color = Color.White
            )
        }
    }
}

@Composable
fun ForegroundPoster(movieDetails: MovieDetailList) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .width(250.dp)
            .padding(top = 45.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movieDetails.posterPath}",
            contentDescription = movieDetails.title,
            Modifier
                .width(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .width(250.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color(0x80000000),
                        )
                    ), shape = RoundedCornerShape(16.dp)
                )
        )
    }
}

@Composable
fun BackGroundPoster(movieDetails: MovieDetailList) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movieDetails.posterPath}",
            contentDescription = movieDetails.title,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.35f)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x80000000),
                            Color.Black
                        ),
                        startY = 500f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
    }
}

@Composable
fun SkeletonDetailsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(250.dp)
                .background(Color.Gray, shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(24.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(25.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(24.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(25.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(24.dp)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(20.dp))

        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.Gray)
        )
    }
}
