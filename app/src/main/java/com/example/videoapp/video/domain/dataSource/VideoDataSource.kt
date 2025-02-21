package com.example.videoapp.video.domain.dataSource

import com.example.videoapp.core.domain.util.NetworkError
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.video.domain.model.Video


interface VideoDataSource {
    suspend fun getAllVideos(page: Int): Result<List<Video>, NetworkError>
}