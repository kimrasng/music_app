package kr.kimrasng.app.music_player.data.model

import java.time.LocalTime

data class artist(
    val Id: Int,
    val KoreanName: String,
    val ForeignName: String,
    val Date: LocalTime,
    val Image: String
)
