package com.example.videoapp.video.presentation.videoList

import com.example.videoapp.core.domain.util.NetworkError

sealed interface VideoListEvent {
    data class Error(val error: NetworkError):VideoListEvent
}