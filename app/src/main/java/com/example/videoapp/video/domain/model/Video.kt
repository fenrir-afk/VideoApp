package com.example.videoapp.video.domain.model


data class Video(
    val description: String,
    val duration: String,
    val id: String,
    val poster: String,
    val thumbnail: String,
    val title: String,
    val urls: Urls
)