package com.bdl.example.yamovies.screens

import GradientProgressIndicator
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.bdl.example.yamovies.R
import com.bdl.example.yamovies.components.RatingStarsItem
import com.bdl.example.yamovies.models.Movie
import com.bdl.example.yamovies.viewModel.MovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(navController: NavHostController) {
    val movieViewModel = viewModel<MovieViewModel>()
    val state = movieViewModel.state
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isGridView = remember { mutableStateOf(true) }  // State to manage the view mode

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.background(Color.Transparent),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp),
                drawerContainerColor = Color.Transparent.copy(alpha = 0.65f)
            ) {
                Text(
                    "Genres",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 26.sp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Divider()
                Spacer(modifier = Modifier.height(2.dp))
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxHeight()
                ) {
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White,
                        ),
                        label = { Text(text = "Popular Movies") },
                        selected = false,
                        onClick = {
                            movieViewModel.getPopularMovies()
                            scope.launch { drawerState.close() }
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(2.dp))
                    NavigationDrawerItem(
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White,
                        ),
                        label = { Text(text = "Top Rated Movies") },
                        selected = false,
                        onClick = {
                            movieViewModel.getTopRatedMovies()
                            scope.launch { drawerState.close() }
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(2.dp))
                    DrawerItems(onGetDetailsById = { genre ->
                        movieViewModel.getDetailsById(genre)
                        scope.launch { drawerState.close() }
                    })
                }
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            modifier = Modifier.background(Color.Transparent),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.background(Color.Transparent),
                    title = {
                        Text(text = state.title, textAlign = TextAlign.Center)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            isGridView.value = !isGridView.value
                        }) {
                            if (isGridView.value) {
                                Icon(
                                    painter = painterResource(id = R.drawable.grid_view),
                                    contentDescription = "Toggle View",
                                    Modifier.size(32.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = "Toggle View",
                                    Modifier.size(32.dp)

                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent.copy(alpha = 0.2f)
                    ),
                )
            },
            content = { paddingValues ->
                if (isGridView.value) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(Color.Transparent),
                        content = {
                            if (state.isLoading && state.movies.isEmpty()) {
                                items(16) {
                                    ShimmerMovieItem()
                                }
                            } else {
                                items(state.movies.size) {
                                    if (it >= state.movies.size - 1 && !state.endReached && !state.isLoading) {
                                        movieViewModel.loadNextItems()
                                    }
                                    MovieItem(
                                        movie = state.movies[it],
                                        navController = navController
                                    )
                                }
                            }

                            if (state.isLoading && state.movies.isNotEmpty()) {
                                item {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        CircularProgressIndicator(color = Color.White)
                                    }
                                }
                            }

                            if (!state.error.isNullOrEmpty()) {
                                item {
                                    Toast.makeText(
                                        LocalContext.current,
                                        state.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    )
                } else {
                    LazyColumn(
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(Color.Transparent),
                        content = {
                            if (state.isLoading && state.movies.isEmpty()) {
                                items(16) {
                                    ShimmerMovieItem()
                                }
                            } else {
                                items(state.movies.size) {
                                    if (it >= state.movies.size - 1 && !state.endReached && !state.isLoading) {
                                        movieViewModel.loadNextItems()
                                    }
                                    MovieListItem(
                                        movie = state.movies[it],
                                        navController = navController
                                    )
                                }
                            }

                            if (state.isLoading && state.movies.isNotEmpty()) {
                                item {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        CircularProgressIndicator(color = Color.White)
                                    }
                                }
                            }

                            if (!state.error.isNullOrEmpty()) {
                                item {
                                    Toast.makeText(
                                        LocalContext.current,
                                        state.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    )
                }
            },
            containerColor = Color.Transparent
        )
    }
}

@Composable
fun ShimmerMovieItem() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.Gray, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.7f)
                .background(Color.Gray, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.5f)
                .background(Color.Gray, shape = RoundedCornerShape(4.dp))
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(movie: Movie, navController: NavHostController) {
    Card(
        Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {
                navController.navigate("Details Screen/${movie.id}")
            },
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Black.copy(0.8f))
                    .padding(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.TopEnd
                ) {
                    SubcomposeAsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop,
                        loading = {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(all = 12.dp)
                                    .size(16.dp),
                                color = Color.White
                            )
                        },
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.no_image),
                                contentDescription = "Fallback image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    )
                    Column(
                        modifier = Modifier
                            .padding(1.dp)
                    ) {
                        GradientProgressIndicator(
                            progress = (movie.voteAverage!! / 10.0).toFloat(),
                            modifier = Modifier.size(38.dp),
                            strokeWidth = 6.dp,
                            gradientStart = Color(0, 33, 111, 255),
                            gradientEnd = Color(75, 123, 236),
                            trackColor = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${(movie.voteAverage!! * 10).toInt()}%",
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                shadow = Shadow(
                                    Color(0, 33, 111, 255), offset = Offset(1f, 1f), blurRadius = 4f
                                )
                            )
                        )
                    }
                }

                Row {
                    Text(
                        text = movie.title!!,
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        style = TextStyle(
                            shadow = Shadow(
                                Color(0, 33, 111, 255), offset = Offset(0.5f, 1f), blurRadius = 3f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))


                }
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListItem(movie: Movie, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clickable {
                navController.navigate("Details Screen/${movie.id}")
            }
            .background(Color.Transparent.copy(0f))
            .wrapContentSize()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(16.dp)

    )
    {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .background(Color.Black.copy(0.8f))
                .padding(8.dp)


        ) {
            SubcomposeAsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(all = 12.dp)
                            .size(16.dp),
                        color = Color.White
                    )
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = "Fallback image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = movie.title!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    style = TextStyle(
                        shadow = Shadow(
                            Color(0, 33, 111, 255), offset = Offset(0.5f, 1f), blurRadius = 3f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingStarsItem(
                        rating = (movie.voteAverage!! / 10.0).toFloat(),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = String.format("%.2f", movie.voteAverage),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        style = TextStyle(
                            shadow = Shadow(
                                Color(0, 33, 111, 255), offset = Offset(1f, 1f), blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }

}

