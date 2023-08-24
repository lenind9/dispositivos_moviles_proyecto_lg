package com.example.dispositivosmoviles.data.entities.jikan.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dispositivosmoviles.logic.data.AnimeData
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class AnimeDataDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val episodes : Int,
    val synopsis: String,
    val image: String
) : Parcelable

fun AnimeDataDB.getAnimeData(): AnimeData{
    return AnimeData(
        id,
        name,
        episodes,
        synopsis,
        image
    )
}