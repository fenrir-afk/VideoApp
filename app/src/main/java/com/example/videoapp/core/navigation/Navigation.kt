package com.example.videoapp.core.navigation

import android.widget.Toast
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.furniturestore.core.navigation.Route
import com.example.videoapp.core.presentation.ObserveAsEvents
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.domain.model.Video
import com.example.videoapp.video.presentation.videoDetail.WatchVideoScreen
import com.example.videoapp.video.presentation.videoList.VideoListActions
import com.example.videoapp.video.presentation.videoList.VideoListScreen
import com.example.videoapp.video.presentation.videoList.VideoListEvent
import com.example.videoapp.video.presentation.videoList.VideoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FurnitureNavigation(
    modifier: Modifier = Modifier,
    viewModel: VideoViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle() // screen state
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            viewModel.loadVideos()
            refreshing = false
        }
    }

    ObserveAsEvents(events = viewModel.event) {event ->
        when(event){
            is VideoListEvent.NetError -> {
                Toast.makeText(
                    context,
                    "Network Error: ${event.error}",
                    Toast.LENGTH_LONG
                ).show()
            }

            is VideoListEvent.DbError -> {
                Toast.makeText(
                    context,
                    "Database Error: ${event.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = Route.VideoGraph) {
            navigation<Route.VideoGraph>(
                startDestination = Route.VideoList
            ) {
                composable<Route.VideoList>(
                    exitTransition = { fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0)}) },
                    popEnterTransition = { fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) }) }
                ) {
                        VideoAppTheme {
                            VideoListScreen(
                                modifier = modifier,
                                onItemClick = { it: Video ->
                                    viewModel.onAction(VideoListActions.OnItemClick(it))
                                    navController.navigate(Route.VideoFullScreen)
                                },
                                refreshing = refreshing,
                                sendToast = {
                                    Toast.makeText(context,"Just design", Toast.LENGTH_LONG).show()
                                },
                                onRefresh = { refreshing = true },
                                state = state
                            )
                        }

                }
                composable<Route.VideoFullScreen>(
                    enterTransition = {
                        fadeIn() + slideIn(initialOffset = { IntOffset(-it.width, 0) })
                    },
                    exitTransition = {
                        fadeOut() + slideOut(targetOffset = { IntOffset(-it.width, 0)})
                    }
                ) {
                    WatchVideoScreen(
                        state = state
                    )
                }
            }
        }
    }
}