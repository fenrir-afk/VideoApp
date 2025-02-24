package com.example.videoapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.videoapp.core.navigation.FurnitureNavigation
import com.example.videoapp.ui.theme.VideoAppTheme
import com.example.videoapp.video.presentation.videoList.VideoViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var screenState = rememberSaveable{ mutableStateOf(true) }
            ScreenOrientationFun(
                modifier = Modifier,
                screenState,
                this
            )
            VideoAppTheme {
                FurnitureNavigation(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = koinViewModel<VideoViewModel>(),
                    screenState
                )
            }
        }
    }
}



@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun ScreenOrientationFun(
    modifier: Modifier = Modifier,
    screenState: MutableState<Boolean>,
    mainActivity: MainActivity
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    LaunchedEffect(screenState.value){
        systemUiController.isSystemBarsVisible = screenState.value // Status & Navigation bars
        if(!screenState.value){
            mainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        if(screenState.value){
            mainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // This is done to switch the screen to portrait mode so that the user understands that the transition from fullscreen mode is complete.
            mainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}


