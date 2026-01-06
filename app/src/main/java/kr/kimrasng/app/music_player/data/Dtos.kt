package kr.kimrasng.app.music_player.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SongsResponse(
    val songs: List<SongDto>
)

@Serializable
data class SongDto(
    val id: Int,
    @SerialName("foreign_title") val foreignTitle: String,
    @SerialName("korean_title") val koreanTitle: String,
    @SerialName("english_title") val englishTitle: String,
    @SerialName("artist_name") val artistName: String,
    @SerialName("filename") val filename: String,
    @SerialName("image_filename") val imageFilename: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("artist_id") val artistId: Int,
    @SerialName("album_id") val albumId: Int
)

@Serializable
data class ArtistsResponse(
    val artists: List<ArtistDto>
)

@Serializable
data class ArtistDto(
    val id: Int,
    @SerialName("korean_name") val koreanName: String,
    @SerialName("foreign_name") val foreignName: String,
    @SerialName("debut_date") val debutDate: String,
    @SerialName("image_filename") val imageFilename: String
)
