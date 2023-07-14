package com.example.dispmoviles.logic.marvelLogic

import android.util.Log
import com.example.dispmoviles.data.connections.ApiConnection
import com.example.dispmoviles.data.endpoints.MarvelEndpoint
import com.example.dispmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.dispmoviles.data.entities.marvel.characters.database.getMarvelChars
import com.example.dispmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispmoviles.logic.data.MarvelChars
import com.example.dispmoviles.logic.data.getMarvelCharsDB
import com.example.dispmoviles.ui.utilities.DispMoviles

class MarvelLogic {

    suspend fun getMarvelChars(name : String, limit : Int): ArrayList<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        ).getCharactersStartWith(name, limit)

        if(response.isSuccessful){
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }
        return itemList
    }

    suspend fun getAllMarvelChars(offset : Int, limit : Int): ArrayList<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()

        val response = ApiConnection.getService(
            ApiConnection.typeApi.Marvel,
            MarvelEndpoint::class.java
        ).getAllMarvelChars(offset, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                Log.d("UCE", response.toString())
                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }

        return itemList
    }

    suspend fun getAllMarvelCharsDB(): List<MarvelChars> {
        var items : ArrayList<MarvelChars> = arrayListOf()
        val items_aux = DispMoviles.getDBInstance().marvelDao().getAllCharacters()
        items_aux.forEach{
            items.add(it.getMarvelChars())
        }
        return items
    }

    suspend fun insertMarvelCharsToDB(items : List<MarvelChars>) {
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }

        DispMoviles
            .getDBInstance()
            .marvelDao()
            .insertMarvelCharacter(itemsDB)
    }
}