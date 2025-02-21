package com.example.videoapp.video.data.networking

import com.example.videoapp.core.data.networking.constructUrl
import com.example.videoapp.core.data.networking.safeCall
import com.example.videoapp.core.domain.util.NetworkError
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.core.domain.util.map
import com.example.videoapp.video.data.mappers.toVideo
import com.example.videoapp.video.data.networking.dto.VideoDto
import com.example.videoapp.video.domain.dataSource.VideoDataSource
import com.example.videoapp.video.domain.model.Video
import io.ktor.client.HttpClient
import io.ktor.client.request.get


const val API_KEY = "1c84b03e7e50086196c752a745fad639"
class VideoDataSourceImp(private val httpClient: HttpClient):VideoDataSource {
    override suspend fun getAllVideos(page: Int): Result<List<Video>, NetworkError> {
        return safeCall<VideoDto>{
            httpClient.get(
                urlString = constructUrl("?api_key=$API_KEY&urls=true&page=$page")
            )
        }.map { response ->
            response.hits.map { it.toVideo() }
        }
    }
}