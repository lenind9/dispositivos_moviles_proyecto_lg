package com.example.dispmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dispmoviles.R
import com.example.dispmoviles.databinding.ActivityMainBinding
import com.example.dispmoviles.databinding.ActivitySecondBinding
import com.example.dispmoviles.ui.fragments.NewFragment
import com.example.dispmoviles.ui.utilities.FragmentsManager
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

        FragmentsManager().replaceFragment(supportFragmentManager,
            binding.frmContainer.id, NewFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {

                    true
                }
                R.id.favoritos -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                    binding.frmContainer.id, NewFragment())
                    true
                }
                R.id.apis -> {

                    true
                }
                else -> false
            }
        }

        initClass()
    }

    override fun onBackPressed() {
        super.onBackPressed()
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