package com.example.videoapp.video.presentation.videoList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoapp.core.domain.util.onError
import com.example.videoapp.core.domain.util.onSuccess
import com.example.videoapp.video.domain.dataSource.LocalVideoDataSource
import com.example.videoapp.video.domain.dataSource.VideoDataSource
import com.example.videoapp.video.domain.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VideoViewModel(
    private val remoteDataSource: VideoDataSource,
    private val localDataSource: LocalVideoDataSource
): ViewModel() {

    private val _state = MutableStateFlow(VideoListState())
    val state = _state.

    onStart {
        loadVideos()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        VideoListState()
    )
    private val _events = Channel<VideoListEvent>()
    val event = _events.receiveAsFlow()

    fun onAction(action: VideoListActions){
        when(action){
            is VideoListActions.OnItemClick -> {
                _state.update { it.copy(
                    selectedItem = action.item
                ) }
            }
        }
    }
    private suspend fun cachingVideos(items: List<Video>) {
        localDataSource.replacePrevVideos(items).onSuccess {
            Log.i("CAHING","The data has been cached successfully")
        }.onError {error ->
            Log.i("CAHING","The data has not been cached successfully")
            _events.send(VideoListEvent.DbError(error))
        }
    }
    private suspend fun getCachedVideos() {
        localDataSource.getAllVideos().onSuccess {items ->
            _state.update {
                it.copy(
                    isLoading = false,
                    videos = items,
                )
            }
        }.onError {error ->
            Log.i("CAHING",error.name)
            _events.send(VideoListEvent.DbError(error))
        }
    }
    fun loadVideos(){
        viewModelScope.launch(Dispatchers.IO) {
            remoteDataSource.getAllVideos(state.value.page).onSuccess { items->
                _state.update {
                    it.copy(
                        page = it.page + 1,
                        isLoading = false,
                        videos = items,
                    )
                }
                cachingVideos(items)
            }.onError { error->
                getCachedVideos()
                _events.send(VideoListEvent.NetError(error))
            }
        }
    }

}