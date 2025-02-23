package com.example.videoapp.video.presentation.videoList.components

import android.util.Log
import kotlin.math.roundToInt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.videoapp.R
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.domain.model.Urls
import com.example.videoapp.video.domain.model.Video


@Composable
fun MovieItemCard(item: Video?, modifier: Modifier) {
    Card(
        modifier = Modifier
            .padding(all = 10.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable {

            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item!!.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                onError = { ex ->
                    Log.e("TAG_IMAGE_ERROR", "Error: ${ex.result}")
                },
                error = {
                    painterResource(R.drawable.ic_error)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.title,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Duration: ${item.duration.toDouble().roundToInt()} sec",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

}
@Preview(showBackground = true)
@Composable
fun MovieItemCardPreview(modifier: Modifier = Modifier) {
    VideoAppTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Создаёт сетку с 2 столбцами
            modifier = Modifier.fillMaxSize()
        ) {
            items(10){
                MovieItemCard(
                    modifier = Modifier,
                    item = Video(
                        description = "An aerial shot of the Palace of Fine Arts, a prominent cultural center in Mexico City. ",
                        id = "ypWCq2mrZf",
                        duration = "16.475000",
                        poster = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=1920",
                        thumbnail = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=640",
                        title = "Palacio de Bellas Artes",
                        urls = Urls(
                            mp4 = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4",
                            mp4Download = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4?download=true",
                            mp4Preview = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/360p.mp4"
                        )
                    )
                )
            }
        }

    }
}
