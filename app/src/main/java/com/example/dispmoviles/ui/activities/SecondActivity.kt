package com.example.dispmoviles.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dispmoviles.R
import com.example.dispmoviles.databinding.ActivityMainBinding
import com.example.dispmoviles.databinding.ActivitySecondBinding
import com.example.dispmoviles.ui.fragments.NewFragment
import com.example.dispmoviles.ui.fragments.SecondFragment
import com.example.dispmoviles.ui.fragments.ThirdFragment
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

        //Cambia el texto del txtView a "Bienvenido"
        var name: String = ""
        binding.txtView.text = "Bienvenido $name"
        Log.d("UCE", "Entrando a Start")

        binding.btnRetorno.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.inicio -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, NewFragment())
                    true
                }
                R.id.favoritos -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, SecondFragment())
                    true
                }
                R.id.apis -> {
                    FragmentsManager().replaceFragment(supportFragmentManager,
                        binding.frmContainer.id, ThirdFragment())
                    true
                }
                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}