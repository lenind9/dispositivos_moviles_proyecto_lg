package com.example.dispositivosmoviles.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivosmoviles.data.dao.jikan.JikanAnimeDataDAO
import com.example.dispositivosmoviles.data.entities.jikan.database.AnimeDataDB

@Database(
    entities = [AnimeDataDB::class],
    version = 1
)

abstract class JikanAnimeConnectionDB : RoomDatabase() {

    abstract fun jikanAnimeDao() : JikanAnimeDataDAO
}