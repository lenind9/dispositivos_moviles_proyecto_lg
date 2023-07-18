package com.example.dispositivosmoviles.ui.utilities

import android.app.Application
import androidx.room.Room
import com.example.dispositivosmoviles.data.connections.MarvelConnectionDB
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB

class DispositivosMoviles: Application() {
//proceso cuando se llama a la API




    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext,
            MarvelConnectionDB::class.java,
            "marvelDB"
        ).build()
    }


    companion object{  //objeto q se crea dentro de un clase
        //no se necesita instancia para ser llamado

        private var db: MarvelConnectionDB?= null

        fun getDbInstance():MarvelConnectionDB{
            return db!!
        }

    }



}