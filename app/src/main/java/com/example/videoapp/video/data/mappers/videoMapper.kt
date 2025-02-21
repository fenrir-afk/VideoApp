package com.example.videoapp.video.data.mappers

import com.example.videoapp.video.data.networking.dto.Hit
import com.example.videoapp.video.data.networking.dto.VideoDto
import com.example.videoapp.video.domain.model.Video

fun Hit.toVideo():Video{
    return Video(
        description = description,
        duration = duration,
        id = id,
        poster = poster,
        thumbnail = thumbnail,
        title = title,
        urls = urls
    )
}