package com.example.videoapp.video.data.networking

import com.example.videoapp.core.domain.util.NetworkError
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.video.domain.dataSource.VideoDataSource
import com.example.videoapp.video.domain.model.Video

class FakeVideoDataSource(
    private val videos: MutableList<Video> = mutableListOf(),
    private var shouldReturnError: Boolean = false,
    private var errorType: NetworkError = NetworkError.UNKNOWN
) : VideoDataSource {

    fun setShouldReturnError(value: Boolean, error: NetworkError = NetworkError.UNKNOWN) {
        shouldReturnError = value
        errorType = error
    }

    override suspend fun getAllVideos(page: Int): Result<List<Video>, NetworkError> {
        return if (shouldReturnError) {
            Result.Error(errorType)
        } else {
            Result.Success(videos.toList()) // Return a copy
        }
    }
}