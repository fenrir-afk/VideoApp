package com.example.videoapp.video.presentation.videoList

import com.example.videoapp.core.domain.util.DataBaseError
import com.example.videoapp.core.domain.util.NetworkError

sealed interface VideoListEvent {
    data class NetError(val error: NetworkError):VideoListEvent
    data class DbError(val error: DataBaseError):VideoListEvent
}