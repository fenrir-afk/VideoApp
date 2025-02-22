package com.example.videoapp.video.presentation.videoDetail


import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.example.videoapp.video.presentation.videoList.VideoListState


@OptIn(UnstableApi::class)
@Composable
fun LiveStreamingScreen(
    state:VideoListState
) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayerManager.getExoPlayer(context) }
    DisposableEffect(
        key1 = Unit,
    ) {
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val uri = Uri.Builder()
            .encodedPath(state.selectedItem!!.urls.mp4Download)
            .build()
        val mediaItem = MediaItem.Builder().setUri(uri).build()

        val internetVideoSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem) // Changed MediaSource

        exoPlayer.setMediaSource(internetVideoSource)
        exoPlayer.prepare()
        onDispose {
            ExoPlayerManager.releaseExoPlayer()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AndroidView(
            modifier =
            Modifier.fillMaxWidth()
                .aspectRatio(1.4f)
                .padding(top = 16.dp)
                .background(Color.Black),
            factory = {
                PlayerView(context).apply {
                    // Connect the ExoPlayer instance to the PlayerView
                    player = exoPlayer
                    // Configure ExoPlayer settings
                    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                    exoPlayer.playWhenReady = false
                    useController = true
                }
            }
        )
    }
}