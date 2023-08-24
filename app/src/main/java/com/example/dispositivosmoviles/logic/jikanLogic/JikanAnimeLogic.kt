package com.example.dispositivosmoviles.logic.jikanLogic

import android.util.Log
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.JikanEndpoint
import com.example.dispositivosmoviles.data.entities.jikan.database.AnimeDataDB
import com.example.dispositivosmoviles.data.entities.jikan.database.getAnimeData
import com.example.dispositivosmoviles.data.entities.jikan.getAnimeData
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.dispositivosmoviles.logic.data.AnimeData
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.data.getAnimeDataDB
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import java.lang.Exception
import java.lang.RuntimeException

class JikanAnimeLogic {

    suspend fun getAllAnimes(): List<AnimeData>{

        var itemList = arrayListOf<AnimeData>()

        var response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes()

        if(response.isSuccessful){
            response.body()!!.data.forEach{
                val m = it.getAnimeData()
                Log.d("UCE", response.toString())
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllAnimeDataDB(): List<AnimeData> {
        var items: ArrayList<AnimeData> = arrayListOf()
        val itemsAux = DispositivosMoviles.getDbInstanceAnime().jikanAnimeDao().getAllAnimes()
        itemsAux.forEach{
            items.add(it.getAnimeData())
        }
        return items
    }

    suspend fun getInitAnime(): List<AnimeData> {
        var items = listOf<AnimeData>()
        try {
            var items = JikanAnimeLogic().getAllAnimeDataDB().toList()
            if(items.isEmpty()) {
                items = (JikanAnimeLogic().getAllAnimes())
                JikanAnimeLogic().insertAnimeDataToDB(items)
            }
            items
        } catch (ex: Exception){
            throw RuntimeException(ex.message)
        } finally {
            return items
        }
    }

    suspend fun insertAnimeDataToDB(items: List<AnimeData>){
        var itemsDB = arrayListOf<AnimeDataDB>()
        items.forEach{
            itemsDB.add( it.getAnimeDataDB())
        }
        DispositivosMoviles
            .getDbInstanceAnime()
            .jikanAnimeDao()
            .insertAnime(itemsDB)
    }

}