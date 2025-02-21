package com.example.videoapp.video.data.networking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    @SerialName("mp4") val mp4: String,
    @SerialName("mp4_download") val mp4Download: String,
    @SerialName("mp4_preview") val mp4Preview: String
)