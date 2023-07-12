package com.example.dispmoviles.logic.marvelLogic

import com.example.dispmoviles.data.connections.ApiConnection
import com.example.dispmoviles.data.endpoints.MarvelEndpoint
import com.example.dispmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.dispmoviles.data.entities.marvel.characters.database.getMarvelChars
import com.example.dispmoviles.data.entities.marvel.characters.getMarvelChars
import com.example.dispmoviles.logic.data.MarvelChars
import com.example.dispmoviles.logic.data.getMarvelCharsDB
import com.example.dispmoviles.ui.utilities.DispMoviles

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

    suspend fun getAllMarvelCharsDB(): List<MarvelChars> {
        var itemList : ArrayList<MarvelChars> = arrayListOf()
        val items_aux = DispMoviles.getDBInstance().marvelDao().getAllCharacters()
        items_aux.forEach {
            itemList.add(it.getMarvelChars())
        }
        return itemList
    }

    suspend fun getInitChars(page : Int){
        marvelCharsItems = withContext(Dispatchers.IO) {
            var marvelCharsItems = MarvelLogic()
                .getAllMarvelCharsDB()
                .toMutableList()

            if(marvelCharsItems.isEmpty()) {
                marvelCharsItems = (MarvelLogic().getAllMarvelChars(
                    0, page * 3))
                MarvelLogic().insertMarvelCharsToDB(marvelCharsItems)
            }
            return@withContext marvelCharsItems
        }
    }

    suspend fun insertMarvelCharsToDB(items : List<MarvelChars>) {
        var itemsDB = arrayListOf<MarvelCharsDB>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }

        DispMoviles
            .getDBInstance()
            .marvelDao()
            .insertMarvelChar(itemsDB)
    }

}