package com.example.dispmoviles.logic.data

import android.os.Parcelable
import com.example.dispmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val name: String,
    val comic: String,
    val image: String
    ): Parcelable

fun MarvelChars.getMarvelCharsDB() : MarvelCharsDB {
    return MarvelCharsDB(
        id,
        name,
        comic,
        image
    )
}