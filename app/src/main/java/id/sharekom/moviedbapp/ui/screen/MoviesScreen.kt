package id.sharekom.moviedbapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import id.sharekom.moviedbapp.BuildConfig
import id.sharekom.moviedbapp.MainActivity
import id.sharekom.moviedbapp.MovieViewModel
import id.sharekom.moviedbapp.R
import id.sharekom.moviedbapp.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(navController: NavController, genre_id: Int, viewModel: MovieViewModel = koinViewModel()) {
    val context = LocalContext.current as MainActivity
    val movies = viewModel.getMoviesByGenre(genre_id)
        .collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Movies") })
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding),
            ) {
                items(count = movies.itemCount) { index ->
                    val item = movies[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(true, onClick = {
                                navController.navigate(Screen.DetailMovieScreen.withArgs(item?.id.toString()))
                            })
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            AsyncImage(
                                modifier = Modifier.width(96.dp),
                                model = BuildConfig.IMG_URL + item?.backdrop_path,
                                placeholder = painterResource(
                                    id = R.drawable.broken_picture
                                ),
                                contentDescription = null
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(16.dp)
                            ) {
                                Text(text = "${item?.original_title}", fontWeight = FontWeight.Bold)
                                Text(modifier = Modifier.padding(vertical = 8.dp),text = context.getString(
                                    R.string.release_date, item?.release_date))
                                Row {
                                    Image(modifier = Modifier
                                        .width(24.dp)
                                        .padding(end = 4.dp), painter = painterResource(id = R.drawable.ic_baseline_star_24), contentDescription = null)
                                    Text(text = context.getString(R.string.rating, String.format("%,.1f", item?.vote_average)))
                                }
                            }
                        }
                    }
                }
            }

            if (movies.loadState.append is LoadState.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}