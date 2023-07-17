package id.sharekom.moviedbapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import id.sharekom.moviedbapp.BuildConfig
import id.sharekom.moviedbapp.MovieViewModel
import id.sharekom.moviedbapp.R
import id.sharekom.moviedbapp.ui.components.DialogReviews
import id.sharekom.moviedbapp.ui.components.DialogVideo
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieScreen(movieId: Int, viewModel: MovieViewModel = koinViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val detailMovie by remember { viewModel.getDetailMovie(movieId) }
    val video by remember { viewModel.getVideos(movieId) }
    var dialogVideoState by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var showReviewsDialogState by remember { mutableStateOf(false) }
    val movieReviews = viewModel.getReviews(movieId).collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()

    if (dialogVideoState) {
        val videoKey = video?.results?.get(0)?.key
        DialogVideo(videoKey = videoKey.toString(), onDismiss = { dialogVideoState = it })
    }

    if (showReviewsDialogState) {
        DialogReviews(movieReviews = movieReviews, onDismiss = { showReviewsDialogState = it })
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text(text = "Detail Movie") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (video?.results?.isNotEmpty() == true) {
                    dialogVideoState = true
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "No video available.", duration = SnackbarDuration.Short
                        )
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_play_circle_outline_24),
                    contentDescription = null
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(scrollState, true)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.width(96.dp),
                        model = BuildConfig.IMG_URL + detailMovie?.backdrop_path,
                        contentDescription = null
                    )
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = detailMovie?.original_title.toString(),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.padding(vertical = 16.dp),
                            text = stringResource(
                                R.string.release_date,
                                detailMovie?.release_date.toString()
                            )
                        )
                        Row {
                            Image(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                                contentDescription = null
                            )
                            Text(
                                text = stringResource(
                                    R.string.rating,
                                    String.format("%,.1f", detailMovie?.vote_average)
                                )
                            )
                        }
                    }
                }

                Text(text = "Overviews : ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = detailMovie?.overview.toString(),
                    textAlign = TextAlign.Justify
                )

                Text(text = "Rate Count : ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = detailMovie?.vote_count.toString()
                )

                Text(text = "Popularity : ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = detailMovie?.popularity.toString()
                )

                Text(text = "Language : ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = detailMovie?.original_language.toString()
                )

                Button(
                    modifier = Modifier.padding(bottom = 16.dp),
                    onClick = {
                        if (movieReviews.itemCount > 0) {
                            showReviewsDialogState = true
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("No reviews.")
                            }
                        }
                    }
                ) {
                    Text(text = "Show Reviews")
                }
            }
        }
    )
}