package com.example.dispositivosmoviles.ui.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : ViewModel() {

    val progressState = MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()

    fun processBackground(value: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)
            delay(value)
            val state1 = View.GONE
            progressState.postValue(state1)
        }
    }

    fun sumInBackground() {
        viewModelScope.launch(Dispatchers.IO) {
            progressState.postValue(View.VISIBLE)

            var total = 0
            for(i in 1 .. 300000){
                total += i
            }

            progressState.postValue(View.GONE)
        }
    }

    suspend fun getMarvelChars(offset : Int, limit : Int) {
        progressState.postValue(View.VISIBLE)
        val newItems = MarvelLogic().getAllMarvelChars(offset, limit)
        items.postValue(newItems)
        progressState.postValue(View.GONE)
    }

}