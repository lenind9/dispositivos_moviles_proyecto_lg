package com.example.dispositivosmoviles.data.dao.jikan

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.jikan.database.AnimeDataDB
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB

@Dao
interface JikanAnimeDataDAO {
    @Query("select * from AnimeDataDB")
    fun getAllAnimes():List<AnimeDataDB>

    @Query("select * from AnimeDataDB where id= :id")
    fun getOneAnime(id: Int) : AnimeDataDB

    @Insert
    fun insertAnime(ch:List<AnimeDataDB>)
}