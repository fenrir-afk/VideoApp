package com.example.videoapp.video.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    @SerialName("hits") val hits: List<Hit>,
)