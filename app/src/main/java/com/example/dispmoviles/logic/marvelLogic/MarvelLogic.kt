package com.example.dispmoviles.logic.marvelLogic

import android.util.Log
import com.example.dispmoviles.data.connections.ApiConnection
import com.example.dispmoviles.data.endpoints.MarvelEndpoint
import com.example.dispmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispmoviles.logic.data.MarvelChars

class MarvelLogic {

    private val key = "f00af94ad24dd1d56b2ea26ae903030e"

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

        if(response != null){
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }
        return itemList
    }
}