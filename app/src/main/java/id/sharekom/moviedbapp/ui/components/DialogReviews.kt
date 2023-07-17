package id.sharekom.moviedbapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import id.sharekom.moviedbapp.BuildConfig
import id.sharekom.moviedbapp.R
import id.sharekom.moviedbapp.data.model.review.ReviewsResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DialogReviews(movieReviews: LazyPagingItems<ReviewsResult>, onDismiss: (state: Boolean) -> Unit) {
    Dialog(onDismissRequest = { onDismiss(false) }) {
        Reviews(movieReviews = movieReviews)
    }
}

@Composable
fun Reviews(movieReviews: LazyPagingItems<ReviewsResult>) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(count = movieReviews.itemCount) { index ->
            val item = movieReviews[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row {
                        AsyncImage(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape),
                            model = BuildConfig.IMG_URL + item?.author_details?.avatar_path,
                            placeholder = painterResource(
                                id = R.drawable.broken_picture
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(bottom = 16.dp, start = 16.dp)
                        ) {
                            Text(text = "${item?.author}", fontWeight = FontWeight.Bold)
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = stringResource(
                                    R.string.date_created,
                                    SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
                                        .format(Date())
                                )
                            )
                        }
                    }
                    Text(text = item?.content.toString(), textAlign = TextAlign.Justify)
                }
            }
        }
    }
}