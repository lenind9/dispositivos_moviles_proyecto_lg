package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityDetailsAnimeItemBinding
import com.example.dispositivosmoviles.logic.data.AnimeData
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.squareup.picasso.Picasso

class DetailsAnimeItem : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsAnimeItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsAnimeItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val item = intent.getParcelableExtra<AnimeData>("item")

        if (item !== null){
            Log.w("UCE", "Details Anime")
            binding.txtName.text = item.name
            var episodios = item.episodes.toString()
            binding.txtEpisodios.text = "Episodios: $episodios"
            Picasso.get().load(item.image).into(binding.imgAnime)
            binding.txtDescription.text = item.synopsis

        }
    }
}