package com.example.videoapp.video.presentation


import android.util.Log
import com.example.videoapp.core.domain.util.NetworkError
import com.example.videoapp.video.data.local.FakeLocalVideoDataSource
import com.example.videoapp.video.data.networking.FakeVideoDataSource
import com.example.videoapp.video.domain.model.Urls
import com.example.videoapp.video.domain.model.Video
import com.example.videoapp.video.presentation.videoList.VideoListActions
import com.example.videoapp.video.presentation.videoList.VideoListEvent
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class VideoViewModelTest {
    private lateinit var fakeRemoteDataSource: FakeVideoDataSource
    private lateinit var fakeLocalDataSource: FakeLocalVideoDataSource
    private lateinit var viewModel: VideoViewModel


    // Sample Video Data
    private val sampleVideos = listOf(
        Video(
            description = "An aerial shot of the Palace of Fine Arts, a prominent cultural center in Mexico City. ",
            id = "ypWCq2mrZf",
            duration = "16.475000",
            poster = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=1920",
            thumbnail = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=640",
            title = "Palacio de Bellas Artes",
            urls = Urls(
                mp4 = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4",
                mp4Download = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4?download=true",
                mp4Preview = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/360p.mp4"
            )
        ),
        Video(
            description = "An aerial shot of the Palace of Fine Arts, a prominent cultural center in Mexico City. ",
            id = "ypWCq2mrZf",
            duration = "16.475000",
            poster = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=1920",
            thumbnail = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=640",
            title = "Palacio de Bellas Artes",
            urls = Urls(
                mp4 = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4",
                mp4Download = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4?download=true",
                mp4Preview = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/360p.mp4"
            )
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0 // или любое другое Int значение
        Dispatchers.setMain(Dispatchers.Unconfined) // Use Unconfined for immediate execution
        fakeRemoteDataSource = FakeVideoDataSource(sampleVideos.toMutableList())
        fakeLocalDataSource = FakeLocalVideoDataSource()
        viewModel = VideoViewModel(fakeRemoteDataSource, fakeLocalDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `loadVideos should update state with videos from remote and cache them`() = runTest {
        val collectJob = launch {
            viewModel.state.collectLatest { state ->
                if (!state.isLoading && state.videos.isNotEmpty()) {
                    assertTrue(!state.isLoading)
                    assertFalse(state.cachedData)
                    assertEquals(sampleVideos, state.videos)
                }
            }
        }

        collectJob.cancel()
    }
    @Test
    fun `loadVideos should load from cache on remote error`() = runBlocking {
        fakeRemoteDataSource.setShouldReturnError(true, NetworkError.SERIALIZATION)
        fakeLocalDataSource.replacePrevVideos(sampleVideos) // Pre-populate cache

        val collectJob = launch {
            viewModel.state.collectLatest {
                assertFalse(it.isLoading)
                assertTrue(it.cachedData)
                assertEquals(sampleVideos, it.videos)
            }
        }
        collectJob.cancel()
    }

    @Test
    fun `onItemClick should update state with selected item`() = runBlocking {
        val firstVideo = sampleVideos.first()
        viewModel.onAction(VideoListActions.OnItemClick(firstVideo))
        val state = viewModel.state.first()
        assertEquals(firstVideo, state.selectedItem)
    }

    @Test
    fun `UpdateList should reload videos`() = runBlocking {
        val newVideos = listOf(Video(
            description = "An aerial shot of the Palace of Fine Arts, a prominent cultural center in Mexico City. ",
            id = "ypWCq2mrZf",
            duration = "16.475000",
            poster = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=1920",
            thumbnail = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/thumbnail?width=640",
            title = "Palacio de Bellas Artes",
            urls = Urls(
                mp4 = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4",
                mp4Download = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/1080p.mp4?download=true",
                mp4Preview = "https://cdn.coverr.co/videos/coverr-palacio-de-bellas-artes-8229/360p.mp4"
            )
        ))
        fakeRemoteDataSource = FakeVideoDataSource(newVideos.toMutableList())
        viewModel = VideoViewModel(fakeRemoteDataSource, fakeLocalDataSource)

        viewModel.onAction(VideoListActions.UpdateList)

        val state = viewModel.state.first()
        assertEquals(newVideos, state.videos)
    }

    @Test
    fun `SetFullScreen should emit SetFullScreen event`() = runBlocking {
        val isFullScreen = true
        var eventEmitted = false

        val job = launch {
            viewModel.event.collect { event ->
                if (event is VideoListEvent.SetFullScreen) {
                    assertEquals(isFullScreen, event.isFullScreen)
                    eventEmitted = true
                }
            }
        }

        viewModel.onAction(VideoListActions.SetFullScreen(isFullScreen))

        assertTrue(eventEmitted)
        job.cancel()
    }
}
