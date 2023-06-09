package com.example.dispmoviles.ui.utilities

import android.app.Application
import com.example.dispmoviles.data.connections.MarvelConnectionDB
import com.example.dispmoviles.data.entities.marvel.characters.database.MarvelCharsDB

class DispMoviles : Application() {

    val name_class : String = "Admin"

    override fun onCreate(){
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB").build()
    }

    companion object{
        private var db : MarvelConnectionDB? = null

        fun getDBInstance() : MarvelConnectionDB {
            //!! -> porque nunca va a ser nula
            return db!!
        }
    }
}