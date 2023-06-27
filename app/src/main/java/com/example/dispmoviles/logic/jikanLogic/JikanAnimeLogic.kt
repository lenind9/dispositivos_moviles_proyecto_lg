package com.example.dispmoviles.logic.jikanLogic

import com.example.dispmoviles.data.connections.ApiConnection
import com.example.dispmoviles.data.endpoints.JikanEndpoint
import com.example.dispmoviles.data.marvel.MarvelChars

class JikanAnimeLogic {

    suspend fun getAllAnimes(): List<MarvelChars> {

        val itemList = arrayListOf<MarvelChars>()
        val response = ApiConnection.getService(
            ApiConnection.typeApi.Jikan,
            JikanEndpoint::class.java
        ).getAllAnimes()

//        var call = ApiConnection.getJikanConnection()
//        val response = call.create(JikanEndpoint::class.java).getAllAnimes()
//
//        var itemList = arrayListOf<MarvelChars>()

        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = MarvelChars(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}