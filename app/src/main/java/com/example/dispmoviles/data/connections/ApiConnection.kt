package com.example.dispmoviles.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    enum class typeApi {
        Jikan, Marvel
    }

    private val API_JIKAN = "https://api.jikan.moe/v4/"
    private val API_MARVEL = "https://gateway.marvel.com/v1/public/"

    private fun getConnection(base: String): Retrofit {
        var retrofit = Retrofit.Builder()
            .baseUrl(base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    //funcion suspendida
    suspend fun <T> getService(api: typeApi, service: Class<T>) : T {
        var BASE = ""
        when(api.name){
            typeApi.Jikan.name -> {
                BASE = API_JIKAN
            }

            typeApi.Marvel.name -> {
                BASE = API_MARVEL
            }
        }
        return getConnection(BASE).create(service)
    }
}