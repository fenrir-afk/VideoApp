package com.example.videoapp.video.presentation.videoList

import androidx.compose.runtime.Immutable
import com.example.videoapp.video.domain.model.Video


@Immutable
data class VideoListState(
    val isLoading:Boolean = true,
    val videos:List<Video> = emptyList(),
    val selectedItem:Video? = null,
    val page:Int = 0
)