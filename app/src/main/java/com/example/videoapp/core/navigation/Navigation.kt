package com.example.videoapp.core.navigation

import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.furniturestore.core.navigation.Route
import com.example.videoapp.core.presentation.ObserveAsEvents
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.presentation.videoDetail.LiveStreamingScreen
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
            is VideoListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(),
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
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                        VideoAppTheme {
                            VideoListScreen(
                                modifier = modifier,
                                onItemClick = {
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
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    }
                ) {
                    LiveStreamingScreen()
                }
            }
        }
    }
}