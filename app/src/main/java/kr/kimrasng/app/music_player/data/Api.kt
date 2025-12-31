package kr.kimrasng.app.music_player.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class Api {
    private val SONG_URL= "https://api.kimrasng.kr/api/music-server/songs"

    suspend fun getSongJson(): String = withContext(Dispatchers.IO) {
        URL(SONG_URL).readText()
    }

    companion object
}