package kr.kimrasng.app.music_player.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kr.kimrasng.app.music_player.data.Api
import kr.kimrasng.app.music_player.data.SongDto
import kr.kimrasng.app.music_player.utils.PlayerUtils
import kr.kimrasng.app.music_player.utils.WebpImageFromUrl

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    var songs by remember { mutableStateOf<List<SongDto>>(emptyList()) }
    var currentSong by remember { mutableStateOf<SongDto?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    var currentPosition by remember { mutableLongStateOf(0L) }
    var totalDuration by remember { mutableLongStateOf(0L) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingState: Boolean) {
                isPlaying = isPlayingState
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    totalDuration = exoPlayer.duration.coerceAtLeast(0L)
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    LaunchedEffect(exoPlayer, isPlaying) {
        while (isPlaying) {
            currentPosition = exoPlayer.currentPosition.coerceAtLeast(0L)
            totalDuration = exoPlayer.duration.coerceAtLeast(0L)
            delay(1000L)
        }
    }

    LaunchedEffect(currentSong) {
        currentSong?.let { song ->
            val mediaItem = MediaItem.fromUri(song.filename)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            if (!exoPlayer.isPlaying) exoPlayer.play()
        } else {
            if (exoPlayer.isPlaying) exoPlayer.pause()
        }
    }

    LaunchedEffect(Unit) {
        try {
            songs = Api.instance.getSongs()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (currentSong != null) {
            WebpImageFromUrl(
                url = currentSong!!.imageFilename,
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 50.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
        } else {
             Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            MusicInfo(
                song = currentSong,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            PlayList(
                songs = songs,
                onSongClick = { song ->
                    currentSong = song
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            PlayBar(
                currentSong = currentSong,
                isPlaying = isPlaying,
                currentPosition = currentPosition,
                totalDuration = totalDuration,
                onSeek = { position ->
                    exoPlayer.seekTo(position)
                    currentPosition = position
                },
                onPlayPauseClick = {
                    if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
                },
                onNextClick = {
                    val next = PlayerUtils.getNextSong(songs, currentSong)
                    if (next != null) currentSong = next
                },
                onPrevClick = {
                    val prev = PlayerUtils.getPrevSong(songs, currentSong)
                    if (prev != null) currentSong = prev
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
