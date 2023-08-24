package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.JikanAnimesBinding
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.example.dispositivosmoviles.logic.data.AnimeData
import com.squareup.picasso.Picasso

class AnimeAdapter(
    private val fnClick: (AnimeData) -> Unit):
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    var items: List<AnimeData> = listOf()

    class AnimeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding: JikanAnimesBinding = JikanAnimesBinding.bind(view)

        fun render(item: AnimeData, fnClick: (AnimeData) -> Unit){
            binding.txtName.text = item.name
            var episodios = item.episodes.toString()
            binding.txtComic.text = "Episodios: $episodios"
            Picasso.get().load(item.image).into(binding.imgImage)

            itemView.setOnClickListener{
                fnClick(item)
                //Snackbar.make(binding.imgMarvel, item.name, Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AnimeViewHolder(
            inflater.inflate(R.layout.jikan_animes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<AnimeData>){
        items = items.plus(newItems)
        notifyDataSetChanged()
    }

}