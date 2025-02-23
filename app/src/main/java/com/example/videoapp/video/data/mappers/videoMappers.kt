package com.example.videoapp.video.data.mappers

import com.example.videoapp.video.data.local.entity.VideoEntity
import com.example.videoapp.video.data.networking.dto.Hit
import com.example.videoapp.video.data.networking.dto.UrlsDto
import com.example.videoapp.video.domain.model.Urls
import com.example.videoapp.video.domain.model.Video

fun Hit.toVideo():Video{
    return Video(
        description = description,
        duration = duration,
        id = id,
        poster = poster,
        thumbnail = thumbnail,
        title = title,
        urls = urls.toUrl()
    )
}
fun VideoEntity.toVideo():Video{
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

fun Video.toVideoEntity():VideoEntity{
    return VideoEntity(
        description = description,
        duration = duration,
        id = id,
        poster = poster,
        thumbnail = thumbnail,
        title = title,
        urls = urls
    )
}
fun UrlsDto.toUrl():Urls{
    return Urls(
        mp4 = mp4,
        mp4Preview = mp4Preview,
        mp4Download = mp4Download
    )
}