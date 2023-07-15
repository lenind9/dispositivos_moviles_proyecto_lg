package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityPrincipalBinding


import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.example.dispositivosmoviles.ui.fragments.SecondFragment
import com.example.dispositivosmoviles.ui.fragments.ThirdFragment
import com.example.dispositivosmoviles.ui.utilities.FragmentsManager

class PrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        //Cambia el texto del txtView a "Bienvenido"
        var name: String = ""
        binding.txtView.text = "Bienvenido $name"
        Log.d("UCE", "Entrando a Start")

        binding.btnRetorno.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java))
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(supportFragmentManager, binding.frmContainer1.id, FirstFragment())
                    true
                }

                R.id.favoritos -> {
                    FragmentsManager().replaceFragment(supportFragmentManager, binding.frmContainer1.id, SecondFragment())
                    true
                }
                R.id.ajustes -> { FragmentsManager().replaceFragment(supportFragmentManager, binding.frmContainer1.id, ThirdFragment())
                    true
                }

                else -> false
            }
        }

    }
    override fun onBackPressed(){
        super.onBackPressed();
    }

}