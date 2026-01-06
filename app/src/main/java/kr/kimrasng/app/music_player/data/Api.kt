package kr.kimrasng.app.music_player.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.URL

class Api {
    private val url = "https://api.kimrasng.kr/api/music-server/"
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getSongs(): List<SongDto> = withContext(Dispatchers.IO) {
        val jsonString = URL(url + "songs").readText()
        json.decodeFromString<SongsResponse>(jsonString).songs
    }

    suspend fun getArtists(): List<ArtistDto> = withContext(Dispatchers.IO) {
        val jsonString = URL(url + "artists").readText()
        json.decodeFromString<ArtistsResponse>(jsonString).artists
    }

    companion object {
        val instance = Api()
    }
}
