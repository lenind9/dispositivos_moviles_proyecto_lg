package com.example.dispmoviles.logic.jikanLogic

import com.example.dispmoviles.data.connections.ApiConnection
import com.example.dispmoviles.data.endpoints.JikanEndpoint
import com.example.dispmoviles.logic.data.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes() : List<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()
        val response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes()

        if(response.isSuccessful){
            response.body()!!.data.forEach{
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.rating,
                    it.synopsis,
                    it.images.jpg.image_url)
                itemList.add(m)
            }
        }

        return itemList
    }
}