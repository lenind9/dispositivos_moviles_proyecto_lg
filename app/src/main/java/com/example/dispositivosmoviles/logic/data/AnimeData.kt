package com.example.dispositivosmoviles.logic.data

import android.os.Parcelable
import com.example.dispositivosmoviles.data.entities.jikan.database.AnimeDataDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeData(
    val id: Int,
    val name: String,
    val episodes : Int,
    val synopsis: String,
    val image: String
) : Parcelable

fun AnimeData.getAnimeDataDB(): AnimeDataDB{
    return AnimeDataDB(
        id,
        name,
        episodes,
        synopsis,
        image
    )
}