package com.example.dispmoviles.data.entities.marvel.characters.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dispmoviles.logic.data.MarvelChars
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val comic: String,
    val image: String
    ): Parcelable

//Desde MarvelCharsDB hacia MarvelChars
fun MarvelCharsDB.getMarvelChars() : MarvelChars {
    return MarvelChars(
        id,
        name,
        comic,
        image
    )
}