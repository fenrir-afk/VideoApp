package com.example.videoapp.video.presentation.videoList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.presentation.videoList.components.MovieItemCard


@Composable
fun VideListScreen(
    modifier: Modifier = Modifier,
    state: VideoListState
) {
    VideoAppTheme {
        if(state.isLoading){
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }else{
            LazyVerticalGrid(
                state = rememberLazyGridState(),
                columns = GridCells.Fixed(2), // Создаёт сетку с 2 столбцами
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.videos) {
                    MovieItemCard(
                        modifier = Modifier,
                        item = it
                    )
                }
            }
        }
    }
}