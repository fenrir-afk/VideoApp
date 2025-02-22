package com.example.videoapp.video.presentation.videoList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.data.networking.dto.Urls
import com.example.videoapp.video.domain.model.Video
import com.example.videoapp.video.presentation.videoList.components.MovieItemCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen(
    modifier: Modifier = Modifier,
    sendToast: () -> Unit,
    onRefresh: () -> Unit,
    state: VideoListState,
    refreshing: Boolean,
    onItemClick: (Video) -> Unit
) {
        if(state.isLoading){
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else{
                SwipeRefresh(
                    modifier = modifier,
                    state = rememberSwipeRefreshState(isRefreshing = refreshing),
                    onRefresh = onRefresh,
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title= { Text(
                                    text = "Video app",
                                    fontWeight = FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.primary,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 4,
                                    fontSize = 22.sp
                                ) },
                                navigationIcon={ IconButton(onClick = {
                                    sendToast()
                                }) { Icon(Icons.Filled.Menu, contentDescription = "Menu") }
                                },
                                colors= TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                                    actionIconContentColor = MaterialTheme.colorScheme.primary)
                            )
                        },
                    ) { innerPadding ->
                        LazyVerticalGrid(
                            state = rememberLazyGridState(),
                            columns = GridCells.Fixed(3), // Создаёт сетку с 3 столбцами
                            modifier = Modifier.fillMaxSize().padding(innerPadding)
                        ) {
                            items(state.videos) {
                                MovieItemCard(
                                    modifier = Modifier.clickable {
                                        onItemClick(it)
                                    },
                                    item = it
                                )
                            }
                        }
                    }
                }
        }
}


@Preview(showBackground = true)
@Composable
fun FurnitureListPreview(modifier: Modifier = Modifier) {
    VideoAppTheme {
        val arr = mutableListOf<Video>()
        var refreshing by remember { mutableStateOf(false) }
        repeat(15){
            arr.add(
                Video(
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
        VideoListScreen(
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            ),
            sendToast = {

            },
            onRefresh = {},
            state = VideoListState(
                videos = arr,
                isLoading = false,
                ),

            refreshing = refreshing,
            onItemClick = {

            }
        )
    }
}