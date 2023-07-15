package com.example.dispositivosmoviles.data.entities.marvel.characters.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dispositivosmoviles.logic.data.MarvelChars
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val comic: String,
    val synopsis: String,
    val image: String
) : Parcelable

fun MarvelCharsDB.getMarvelChars():MarvelChars{
    return MarvelChars(
        id,
        name,
        comic,
        synopsis,
        image
    )
}