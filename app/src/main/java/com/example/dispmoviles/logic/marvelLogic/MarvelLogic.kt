package com.example.dispmoviles.logic.marvelLogic

import android.util.Log
import com.example.dispmoviles.data.connections.ApiConnection
import com.example.dispmoviles.data.endpoints.MarvelEndpoint
import com.example.dispmoviles.data.marvel.MarvelChars

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
                var comic : String = "Not available"
                if (it.comics.items.size > 0) {
                    comic = it.comics.items[0].name
                }
                val m = MarvelChars(
                    it.id,
                    it.name,
                    comic,
                    it.thumbnail.path + "." + it.thumbnail.extension
                )
                itemList.add(m)
            }
        } else {
            Log.d("UCE", response.toString())
        }
        return itemList
    }
}