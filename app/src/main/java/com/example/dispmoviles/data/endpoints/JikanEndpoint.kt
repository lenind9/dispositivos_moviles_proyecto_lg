package com.example.dispmoviles.data.endpoints

import com.example.dispmoviles.data.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint {

    @GET("top/anime")
    suspend fun getAllAnimes() : Response<JikanAnimeEntity>
}