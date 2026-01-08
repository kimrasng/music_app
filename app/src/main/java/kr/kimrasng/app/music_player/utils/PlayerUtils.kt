package kr.kimrasng.app.music_player.utils

import kr.kimrasng.app.music_player.data.SongDto

object PlayerUtils {
    fun getNextSong(songs: List<SongDto>, currentSong: SongDto?): SongDto? {
        if (currentSong == null || songs.isEmpty()) return null
        
        val currentIndex = songs.indexOf(currentSong)
        // 마지막 곡이 아니면 다음 곡 반환
        return if (currentIndex != -1 && currentIndex < songs.lastIndex) {
            songs[currentIndex + 1]
        } else {
            null // 마지막 곡이면 null 반환 (또는 반복 재생 로직 추가 가능)
        }
    }

    fun getPrevSong(songs: List<SongDto>, currentSong: SongDto?): SongDto? {
        if (currentSong == null || songs.isEmpty()) return null
        
        val currentIndex = songs.indexOf(currentSong)
        // 첫 번째 곡이 아니면 이전 곡 반환
        return if (currentIndex > 0) {
            songs[currentIndex - 1]
        } else {
            null
        }
    }
}
