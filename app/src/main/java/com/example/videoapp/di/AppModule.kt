package com.example.videoapp.di

import com.example.videoapp.core.data.networking.HttpClientFactory
import com.example.videoapp.video.data.local.LocalVideoDataSourceImp
import com.example.videoapp.video.data.local.database.VideoDatabase
import com.example.videoapp.video.data.networking.VideoDataSourceImp
import com.example.videoapp.video.domain.dataSource.LocalVideoDataSource
import com.example.videoapp.video.domain.dataSource.VideoDataSource
import com.example.videoapp.video.presentation.VideoViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //networking
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::VideoDataSourceImp).bind<VideoDataSource>()
    viewModelOf(::VideoViewModel)

    //database
    single { VideoDatabase.getDatabase(get()) }
    singleOf(::LocalVideoDataSourceImp).bind<LocalVideoDataSource>()
    single { get<VideoDatabase>().videoDao() }
}