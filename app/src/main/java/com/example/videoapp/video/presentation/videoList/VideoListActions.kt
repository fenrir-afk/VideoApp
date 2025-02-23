package com.example.videoapp.video.presentation.videoList

import com.example.videoapp.video.domain.model.Video

sealed interface VideoListActions {
    data class OnItemClick(val item: Video):VideoListActions
    data object UpdateList : VideoListActions
    data class SetFullScreen(val isFullScreen:Boolean) : VideoListActions
}