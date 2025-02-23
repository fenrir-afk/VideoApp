package com.example.videoapp.video.data.local

import com.example.videoapp.core.data.local.checkExceptions
import com.example.videoapp.core.domain.util.DataBaseError
import com.example.videoapp.core.domain.util.Result
import com.example.videoapp.core.domain.util.map
import com.example.videoapp.video.data.local.dao.VideoDao
import com.example.videoapp.video.data.local.entity.VideoEntity
import com.example.videoapp.video.data.mappers.toVideo
import com.example.videoapp.video.data.mappers.toVideoEntity
import com.example.videoapp.video.domain.dataSource.LocalVideoDataSource
import com.example.videoapp.video.domain.model.Video


class LocalVideoDataSourceImp(
    private val videoDao: VideoDao
):LocalVideoDataSource {
    override suspend fun getAllVideos(): Result<List<Video>, DataBaseError> {
        return  checkExceptions<List<VideoEntity>>{
            videoDao.getAllVideos()
        }.map { videoEntityList ->
            videoEntityList.map { it.toVideo() }
        }
    }
    override suspend fun replacePrevVideos(videos: List<Video>): Result<Unit, DataBaseError> {
        return  checkExceptions<Unit>{
            videoDao.replaceAllVideos(videos.map {
                it.toVideoEntity()
            })
        }
    }
    override suspend fun addVideos(videos: List<Video>): Result<Unit, DataBaseError> {
        return checkExceptions<Unit> {
            videoDao.insertVideos(videos.map {
                it.toVideoEntity()
            })
        }
    }
}