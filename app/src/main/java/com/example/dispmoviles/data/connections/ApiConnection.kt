package com.example.dispmoviles.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    fun getJikanConnection(): Retrofit {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jinkan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}