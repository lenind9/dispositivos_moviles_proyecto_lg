package com.example.dispmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dispmoviles.R
import com.example.dispmoviles.data.marvel.MarvelChars

import com.example.dispmoviles.databinding.FragmentNewBinding
import com.example.dispmoviles.logic.jikanLogic.JikanAnimeLogic
import com.example.dispmoviles.logic.lists.ListItems
import com.example.dispmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispmoviles.ui.activities.DetailsMarvelItem
import com.example.dispmoviles.ui.activities.MainActivity
import com.example.dispmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewFragment : Fragment() {

    private lateinit var binding: FragmentNewBinding
    private lateinit var lmanager : LinearLayoutManager
    private var rvAdapter : MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewBinding.inflate(layoutInflater, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        return binding.root
    }

    override fun onStart(){
        super.onStart()

        val names = arrayListOf<String>(
            "Carlos",
            "Xavier",
            "Andrés",
            "Pepe",
            "Mariano",
            "Rosa")

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        binding.spinner.adapter = adapter
        chargeDataRV("cap")

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV("cap")
            binding.rvSwipe.isRefreshing = false
        }

        binding.rvMarvelChars.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //dy: posicion vertical
                    if(dy > 0){

                        //cuantos elems han pasado
                        val v = lmanager.childCount
                        //mi posicion actual
                        val p = lmanager.findFirstVisibleItemPosition()
                        //cuantos elems tengo en total
                        val t = lmanager.itemCount

                        //si la posicion actual mas los elems que han pasado, entonces tengo que recargar
                        if((v + p) >= t){
                            //en corutina IO
                            lifecycleScope.launch(Dispatchers.IO){
                                val newItems = JikanAnimeLogic().getAllAnimes()
                                /*val newItems = MarvelLogic().getMarvelChars(
                                    name = "spider",
                                    limit = 20
                                )*/
                                //cambio de corutina a Main
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(newItems)
                                }
                            }
                        }

                    }
                }
        })
    }

    fun sendMarvelItem(item: MarvelChars) {
        //Intents solo estan en fragments y activities
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun corrutine(){
        lifecycleScope.launch(Dispatchers.Main){
            var name = "Lenin"

            name = withContext(Dispatchers.IO){
                name = "David"
                return@withContext name
            }
        }
    }

    // Serializacion: pasar de un objeto a un string para poder enviarlo por medio de la web, usa obj JSON
    // Parceables: Mucho mas eficiente que la serializacion pero su implementacion es compleja, pero existen plugins que nos ayudan
    fun chargeDataRV(search: String) {
        lifecycleScope.launch(Dispatchers.IO){
            rvAdapter.items = JikanAnimeLogic().getAllAnimes()
                //ListItems().returnMarvelChars()
                //JikanAnimeLogic().getAllAnimes()
                //MarvelLogic().getMarvelChars(name = search, limit = 20)

            //las funciones lambda se llaman con {} y van fuera del parentesis
            //{ sendMarvelItem(it) }

            withContext(Dispatchers.Main){
                with(binding.rvMarvelChars){
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }
            }

        }
    }
}