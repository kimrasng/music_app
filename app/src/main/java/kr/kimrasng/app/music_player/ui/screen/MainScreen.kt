package kr.kimrasng.app.music_player.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kr.kimrasng.app.music_player.data.Api
import kr.kimrasng.app.music_player.data.SongDto
import kr.kimrasng.app.music_player.utils.PlayerUtils

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    var songs by remember { mutableStateOf<List<SongDto>>(emptyList()) }
    var currentSong by remember { mutableStateOf<SongDto?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlayingState: Boolean) {
                isPlaying = isPlayingState
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
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

    Column(modifier = modifier.fillMaxSize()) {
        if (currentSong != null) {
            MusicInfo(
                song = currentSong!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("재생할 음악을 선택해주세요")
            }
        }

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
            onPlayPauseClick = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
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
