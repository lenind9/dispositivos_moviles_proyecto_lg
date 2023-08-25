package com.example.dispositivosmoviles.ui.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic

class QuizViewModel : ViewModel() {
    //val progressState = MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()

    suspend fun getMarvelChars(offset : Int, limit : Int) {
        //progressState.postValue(View.VISIBLE)
        val newItems = MarvelLogic().getAllMarvelChars(offset, limit)
        items.postValue(newItems)
        //progressState.postValue(View.GONE)
    }
}