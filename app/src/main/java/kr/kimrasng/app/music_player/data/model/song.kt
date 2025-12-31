package kr.kimrasng.app.music_player.data.model

import java.time.LocalDate

data class song(
    val Id: Int,
    val Title: String,
    val Artist: artist,
    val MusicFile: String,
    val ImgFile: String,
    val Date: LocalDate,
    val Album: String
)
