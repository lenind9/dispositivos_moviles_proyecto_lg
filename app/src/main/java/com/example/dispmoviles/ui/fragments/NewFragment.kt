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
import com.example.dispmoviles.logic.data.MarvelChars

import com.example.dispmoviles.databinding.FragmentNewBinding
import com.example.dispmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispmoviles.ui.activities.DetailsMarvelItem
import com.example.dispmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewFragment : Fragment() {

    private lateinit var binding: FragmentNewBinding
    private var rvAdapter : MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }
    private lateinit var lManager : LinearLayoutManager
    private lateinit var gManager : GridLayoutManager
    private var page = 1

    private var marvelCharsItems : MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewBinding.inflate(layoutInflater, container, false)
        lManager = LinearLayoutManager(
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
            android.R.layout.simple_spinner_item,
            names
        )

        binding.spinner.adapter = adapter
        chargeDataRVDB(5)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVDB(5)
            binding.rvSwipe.isRefreshing = false
            lManager.scrollToPositionWithOffset(5, 20)
        }

        binding.rvMarvelChars.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy > 0){
                        val v = lManager.childCount //cuantos elems han pasado
                        val p = lManager.findFirstVisibleItemPosition() //mi posicion actual
                        val t = lManager.itemCount //cuantos elems tengo en total

                        //si la posicion actual mas los elems que han pasado, entonces tengo que recargar
                        if((v + p) >= t){
                            //en corutina IO
                            lifecycleScope.launch(Dispatchers.IO){
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
        i.putExtra("item", item)
        startActivity(i)
    }

    // Serializacion: pasar de un objeto a un string para poder enviarlo por medio de la web, usa obj JSON
    // Parceables: Mucho mas eficiente que la serializacion pero su implementacion es compleja, pero existen plugins que nos ayudan
    fun chargeDataRVAPI(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main){
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelCharsDB().toMutableList())
            }
            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply{
                this.adapter = rvAdapter
                this.layoutManager = gManager

                gManager.scrollToPositionWithOffset(pos, 10)
            }

        }
        page ++
    }

    fun chargeDataRVDB(pos: Int) {
        lifecycleScope.launch(Dispatchers.Main){

            marvelCharsItems = withContext(Dispatchers.IO) {
                var items = MarvelLogic()
                    .getAllMarvelCharsDB()
                    .toMutableList()

                if(items.isEmpty()) {
                    items = (MarvelLogic().getAllMarvelChars(
                        0, page * 3))
                    MarvelLogic().insertMarvelCharsToDB(items)
                }

                return@withContext items
            }
        }

        rvAdapter.items = marvelCharsItems

        binding.rvMarvelChars.apply{
            this.adapter = rvAdapter
            this.layoutManager = gManager
            gManager.scrollToPositionWithOffset(pos, 10)
        }
        page++
    }
}