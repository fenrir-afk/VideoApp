package com.example.videoapp.video.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.videoapp.video.data.local.entity.VideoEntity


@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    fun getAllVideos(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)


    @Query("DELETE FROM videos")
    suspend fun deleteAllVideos()

    @Transaction
    suspend fun replaceAllVideos(videos: List<VideoEntity>) {
        deleteAllVideos()  // Удаляем все существующие видео
        insertVideos(videos) // Вставляем новые видео
    }
}