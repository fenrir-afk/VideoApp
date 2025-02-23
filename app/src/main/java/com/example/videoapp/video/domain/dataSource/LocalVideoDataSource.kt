package com.example.videoapp.video.domain.dataSource

import com.example.videoapp.core.domain.util.DataBaseError
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.video.domain.model.Video

interface LocalVideoDataSource {
    suspend fun getAllVideos(): Result<List<Video>, DataBaseError>
    suspend fun addVideos(videos: List<Video>): Result<Unit, DataBaseError>
    suspend fun replacePrevVideos(videos: List<Video>): Result<Unit, DataBaseError>
}