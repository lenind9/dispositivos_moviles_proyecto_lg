package com.example.dispmoviles.data.connections


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispmoviles.data.dao.marvel.MarvelCharsDAO
import com.example.dispmoviles.data.entities.marvel.characters.database.MarvelCharsDB

@Database(
    entities = [MarvelCharsDB::class],
    version = 1)
abstract class MarvelConnectionDB : RoomDatabase(){

    abstract fun marvelDao(): MarvelCharsDAO

}