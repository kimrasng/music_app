package kr.kimrasng.app.music_player.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class Api {
    private val url= "https://api.kimrasng.kr/api/music-server/"

    suspend fun getSongJson(): String = withContext(Dispatchers.IO) {
        URL(url+"songs").readText()
    }
    suspend fun getArtistJson(): String = withContext(Dispatchers.IO) {
        URL(url+"artists").readText()
    }

    companion object
}