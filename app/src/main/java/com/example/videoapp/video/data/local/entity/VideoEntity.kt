package com.example.videoapp.video.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videoapp.video.domain.model.Urls

@Entity(tableName = "Videos")
data class VideoEntity(
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "duration")
    val duration: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "poster")
    val poster: String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "title")
    val title: String,
    @Embedded
    val urls: Urls
)
