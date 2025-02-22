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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

object ExoPlayerManager {
    private var exoPlayer: ExoPlayer? = null

    fun getExoPlayer(context: Context): ExoPlayer {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }
        return exoPlayer!!
    }

    fun releaseExoPlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }
}


@OptIn(UnstableApi::class)
@Composable
fun LiveStreamingScreen() {

    // Obtain the current context and lifecycle owner using LocalContext and LocalLifecycleOwner
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Remember the ExoPlayer instance to persist across recompositions
    val exoPlayer = remember { ExoPlayerManager.getExoPlayer(context) }

    // Launch an effect to initialize ExoPlayer and set up the media source
    LaunchedEffect(key1 = Unit) {

        // Create a data source factory for handling media requests
        val dataSourceFactory = DefaultHttpDataSource.Factory()

        // Define the URI for the sample HLS stream
        val uri = Uri.Builder()
            .encodedPath("https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4?download=true")
            .build()
        val mediaItem = MediaItem.Builder().setUri(uri).build()

        // Create an HlsMediaSource from the media item for handling HTTP Live Streaming (HLS) content
        val internetVideoSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem) // Changed MediaSource

        exoPlayer.setMediaSource(internetVideoSource)
        exoPlayer.prepare()

        // Will be used in later implementation for Equalizer
        //viewModel.onStart(exoPlayer.audioSessionId)


    }
    Box(modifier = Modifier.fillMaxSize()) {
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