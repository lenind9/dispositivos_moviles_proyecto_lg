package com.example.dispmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dispmoviles.R
import com.example.dispmoviles.databinding.ActivityMainBinding
import com.example.dispmoviles.databinding.ActivitySecondBinding
import com.example.dispmoviles.ui.fragments.NewFragment
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        var name : String = ""
//        intent.extras.let {
//            name = it?.getString("var1")!!
//        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {

                    val frag = NewFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(binding.frmContainer.id, frag)
                    //cada clic que se haga se añade un fragment a la pila de navegacion del proyecto
                    transaction.addToBackStack(null)
                    transaction.commit()
                    // Respond to navigation item 1 click
                    true
                }
                R.id.favoritos -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.apis -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }

        initClass()
    }

    fun initClass(){
        /*Log.d("uce", "Entrando a start")  debug en la terminal*/
        binding.btnRetorno.setOnClickListener{
            Log.d("UCE", "Entrando al click de retorno")
            var intent= Intent(this, ActivityMainBinding::class.java)
            startActivity(intent)

            /*Snackbar.make(
                binding.loginSegundo,"regresando",
                Snackbar.LENGTH_LONG).show()*/
        }
    }
}