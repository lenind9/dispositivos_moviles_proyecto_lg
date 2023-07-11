package com.example.dispmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispmoviles.R
import com.example.dispmoviles.logic.data.MarvelChars

import com.example.dispmoviles.databinding.FragmentNewBinding
import com.example.dispmoviles.logic.jikanLogic.JikanAnimeLogic
import com.example.dispmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispmoviles.ui.activities.DetailsMarvelItem
import com.example.dispmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewFragment : Fragment() {

    private var page = 1

    private lateinit var binding: FragmentNewBinding
    private lateinit var lmanager : LinearLayoutManager
    private lateinit var gManager : GridLayoutManager
    private var rvAdapter : MarvelAdapter =
        MarvelAdapter { sendMarvelItem(it) }

    private var marvelCharsItems : MutableList<MarvelChars> = mutableListOf<MarvelChars>()

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
        gManager = GridLayoutManager(requireActivity(), 2)
        return binding.root
    }

    override fun onStart(){
        super.onStart()

        val names = arrayListOf<String>("Carlos", "Xavier", "Andrés", "Pepe", "Mariano", "Rosa")

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        binding.spinner.adapter = adapter
        chargeDataRVDB(5)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVDB(5)
            binding.rvSwipe.isRefreshing = false
            lmanager.scrollToPositionWithOffset(5, 20)
        }

        binding.rvMarvelChars.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy > 0){
                        val v = lmanager.childCount //cuantos elems han pasado
                        val p = lmanager.findFirstVisibleItemPosition() //mi posicion actual
                        val t = lmanager.itemCount //cuantos elems tengo en total

                        //si la posicion actual mas los elems que han pasado, entonces tengo que recargar
                        if((v + p) >= t){
                            //en corutina IO
                            lifecycleScope.launch(Dispatchers.IO){
                                //val newItems = JikanAnimeLogic().getAllAnimes()
                                val newItems = with(Dispatchers.IO) {
                                    MarvelLogic().getAllMarvelChars(0, page * 3)
                                    //JikanAnimeLogic().getAllAnimes()
                                }
                                    rvAdapter.updateListItems(newItems)
                            }
                        }

                    }
                }
            })

        //se importa el que tiene llaves y dice Editable
        binding.txtFilter.addTextChangedListener{ filterText ->
            val newItems = marvelCharsItems.filter {
                    items -> items.name.lowercase().contains(
                filterText.toString().lowercase())
            }
            rvAdapter.replaceListItems(newItems)
        }
    }

    fun sendMarvelItem(item: MarvelChars) {
        //Intents solo estan en fragments y activities
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    // Serializacion: pasar de un objeto a un string para poder enviarlo por medio de la web, usa obj JSON
    // Parceables: Mucho mas eficiente que la serializacion pero su implementacion es compleja, pero existen plugins que nos ayudan
    fun chargeDataRV(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main){
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(
                    0, page * 3))
            }
            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply{
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }

        }
    }

    fun chargeDataRVDB(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main){

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

        rvAdapter.items = marvelCharsItems

        binding.rvMarvelChars.apply{
            this.adapter = rvAdapter
            this.layoutManager = lmanager
            gManager.scrollToPositionWithOffset(pos, 10)
        }
        page++
    }
}