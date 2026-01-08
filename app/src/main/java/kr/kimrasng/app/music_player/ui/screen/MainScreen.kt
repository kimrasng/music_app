package kr.kimrasng.app.music_player.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kr.kimrasng.app.music_player.data.Api
import kr.kimrasng.app.music_player.data.SongDto
import kr.kimrasng.app.music_player.utils.PlayerUtils

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    // 1. 상태 관리
    var songs by remember { mutableStateOf<List<SongDto>>(emptyList()) }
    var currentSong by remember { mutableStateOf<SongDto?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // 2. 데이터 로딩
    LaunchedEffect(Unit) {
        try {
            songs = Api.instance.getSongs()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 3. 레이아웃
    Column(modifier = modifier.fillMaxSize()) {
        // 상단: 현재 곡 정보
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

        // 중간: 플레이리스트
        PlayList(
            songs = songs,
            onSongClick = { song ->
                currentSong = song
                isPlaying = true
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        // 하단: 플레이바
        PlayBar(
            currentSong = currentSong,
            isPlaying = isPlaying,
            onPlayPauseClick = { isPlaying = !isPlaying },
            onNextClick = {
                val nextSong = PlayerUtils.getNextSong(songs, currentSong)
                if (nextSong != null) {
                    currentSong = nextSong
                }
            },
            onPrevClick = {
                val prevSong = PlayerUtils.getPrevSong(songs, currentSong)
                if (prevSong != null) {
                    currentSong = prevSong
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
