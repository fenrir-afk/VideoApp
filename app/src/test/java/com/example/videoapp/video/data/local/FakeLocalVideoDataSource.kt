package com.example.videoapp.video.data.local

import com.example.videoapp.core.domain.util.DataBaseError
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.video.domain.dataSource.LocalVideoDataSource
import com.example.videoapp.video.domain.model.Video


class FakeLocalVideoDataSource(
    private val videos: MutableList<Video> = mutableListOf(),
    private var shouldReturnError: Boolean = false,
    private var errorType: DataBaseError = DataBaseError.UNKNOWN_DB_ERROR
) : LocalVideoDataSource {

    fun setShouldReturnError(value: Boolean, error: DataBaseError = DataBaseError.UNKNOWN_DB_ERROR) {
        shouldReturnError = value
        errorType = error
    }

    override suspend fun getAllVideos(): Result<List<Video>, DataBaseError> {
        return if (shouldReturnError) {
            Result.Error(errorType)
        } else {
            Result.Success(videos.toList()) // Return a copy to prevent modification
        }
    }

    override suspend fun replacePrevVideos(videos: List<Video>): Result<Unit, DataBaseError> {
        return if (shouldReturnError) {
            Result.Error(errorType)
        } else {
            this.videos.clear()
            this.videos.addAll(videos)
            Result.Success(Unit)
        }
    }

    override suspend fun addVideos(videos: List<Video>): Result<Unit, DataBaseError> {
        return if (shouldReturnError) {
            Result.Error(errorType)
        } else {
            this.videos.addAll(videos)
            Result.Success(Unit)
        }
    }
}