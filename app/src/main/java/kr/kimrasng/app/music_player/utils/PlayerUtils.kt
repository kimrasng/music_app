package kr.kimrasng.app.music_player.utils

import kr.kimrasng.app.music_player.data.SongDto

object PlayerUtils {

    fun getNextSong(songs: List<SongDto>, currentSong: SongDto?): SongDto? {
        if (currentSong == null || songs.isEmpty()) return null
        
        val currentIndex = songs.indexOf(currentSong)
        return if (currentIndex != -1 && currentIndex < songs.lastIndex) {
            songs[currentIndex + 1]
        } else {
            null 
        }
    }

    fun getPrevSong(songs: List<SongDto>, currentSong: SongDto?): SongDto? {
        if (currentSong == null || songs.isEmpty()) return null
        
        val currentIndex = songs.indexOf(currentSong)
        return if (currentIndex > 0) {
            songs[currentIndex - 1]
        } else {
            null
        }
    }
}
