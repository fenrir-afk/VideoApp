package com.example.videoapp.video.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hit(
    @SerialName("description") val description: String,
    @SerialName("duration") val duration: String,
    @SerialName("id") val id: String,
    @SerialName("poster") val poster: String,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("title") val title: String,
    @SerialName("urls") val urls: UrlsDto,
)