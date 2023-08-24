package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.databinding.FragmentThirdBinding
import com.example.dispositivosmoviles.logic.data.AnimeData
import com.example.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivosmoviles.ui.activities.DetailsAnimeItem
import com.example.dispositivosmoviles.ui.adapters.AnimeAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding
    private lateinit var lManager: LinearLayoutManager

    private var rvAdapter : AnimeAdapter = AnimeAdapter { sendMarvelItem(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(
            layoutInflater, container, false)

        lManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        chargeDataRV()

        binding.rvSwipeAnime.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipeAnime.isRefreshing = false
        }

        binding.rvAnime.addOnScrollListener(
            object: RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //dy: posicion vertical
                    if(dy > 0){
                        //cuantos elems han pasado
                        val v = lManager.childCount
                        //mi posicion actual
                        val p = lManager.findFirstVisibleItemPosition()
                        //cuantos elems tengo en total
                        val t = lManager.itemCount

                        //si la posicion actual mas los elems que han pasado, entonces tengo que recargar
                        if((v + p) >= t){
                            //en corutina IO
                            lifecycleScope.launch(Dispatchers.IO){
                                val newItems = JikanAnimeLogic().getAllAnimes()
                                withContext(Dispatchers.Main){
                                    rvAdapter.updateListItems(newItems)
                                }
                            }
                        }

                    }
                }
            })
    }

    private fun sendMarvelItem(item: AnimeData) {
        val i = Intent(requireActivity(), DetailsAnimeItem::class.java)
        i.putExtra("item", item)
        startActivity(i)
    }

    private fun chargeDataRV() {
        lifecycleScope.launch(Dispatchers.IO){
            rvAdapter.items = JikanAnimeLogic().getAllAnimes()

            withContext(Dispatchers.Main){
                with(binding.rvAnime){
                    this.adapter = rvAdapter
                    this.layoutManager = lManager
                }
            }
        }
    }


}