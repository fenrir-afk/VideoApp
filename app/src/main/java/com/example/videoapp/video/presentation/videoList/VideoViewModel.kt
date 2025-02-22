package com.example.videoapp.video.presentation.videoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoapp.core.domain.util.onError
import com.example.videoapp.core.domain.util.onSuccess
import com.example.videoapp.video.domain.dataSource.VideoDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VideoViewModel(
    private val dataSource: VideoDataSource
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
    fun loadVideos(){
        viewModelScope.launch {
            dataSource.getAllVideos(state.value.page).onSuccess { items->
                _state.update {
                    it.copy(
                        page = it.page + 1,
                        isLoading = false,
                        videos = items,
                    )
                }
            }.onError { error->
                _state.update { it.copy(isLoading = false) }
                _events.send(VideoListEvent.Error(error))
            }
        }
    }
}