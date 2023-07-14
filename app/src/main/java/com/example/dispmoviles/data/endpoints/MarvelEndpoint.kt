package com.example.dispmoviles.data.endpoints

import com.example.dispmoviles.data.entities.marvel.characters.MarvelApiChars
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelEndpoint {

    @GET("characters")
    suspend fun getCharactersStartWith(
        @Query("nameStartsWith") name : String,
        @Query("limit") limit : Int,
        @Query("ts") ts : String = "uce",
        @Query("apikey") apikey : String = "48ed26ff242038147ce24450236a7ec2",
        @Query("hash") hash : String = "e39fb11ad271b98d8cac028063ce639b"
    ) : Response<MarvelApiChars>

    @GET("characters")
    suspend fun getAllMarvelChars(
        @Query("offset") offset : Int,
        @Query("limit") limit : Int,
        @Query("ts") ts : String = "uce",
        @Query("apikey") apikey : String = "48ed26ff242038147ce24450236a7ec2",
        @Query("hash") hash : String = "e39fb11ad271b98d8cac028063ce639b"
    ) : Response<MarvelApiChars>
}