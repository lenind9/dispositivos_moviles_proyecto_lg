package com.example.dispositivosmoviles.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.ui.validator.LoginValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initClass()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initClass() {
        binding.btnLogin.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtName.text.toString(), binding.txtPass.text.toString()
            )

            if(check){
                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(binding.txtName.text.toString())
                }

                //Intent explicito
                var intent = Intent(this, PrincipalActivity::class.java)

                intent.putExtra("var1", binding.txtName.text.toString())
                intent.putExtra("var2", 2)
                startActivity(intent)
            }else{
                var snackbar = Snackbar.make(binding.btnSignup,
                    "Usuario o contraseña inválidos",
                    Snackbar.LENGTH_LONG)
                //snackbar.setBackgroundTint(ContextCompat.getColor(binding.root.context, R.color.principal_color_dm))
                snackbar.setBackgroundTint(getResources().getColor(R.color.black))
                snackbar.show()
            }
        }

        binding.btnTwitter.setOnClickListener {
            /*val intent = Intent(
                Intent.ACTION_VIEW,
                //Uri.parse("http://google.com.ec")
                Uri.parse("tel:+593995943030")
            )*/

            val intent = Intent(
                Intent.ACTION_WEB_SEARCH
            )
            //abre la barra de busqueda de Google
            intent.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            //busca steam en el navegador
            intent.putExtra(SearchManager.QUERY, "steam")
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()) {
            resultActivity ->

            //contrato con las clausulas a ejecutar
            when(resultActivity.resultCode) {
                RESULT_OK -> {
                    Snackbar.make(
                        binding.txtName,
                        "Resultado existoso",
                        Snackbar.LENGTH_LONG).show()
                }
                RESULT_CANCELED -> {
                    Snackbar.make(
                        binding.txtName,
                        "Resultado fallido",
                        Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    Snackbar.make(
                        binding.txtName,
                        "Resultado dudoso",
                        Snackbar.LENGTH_LONG).show()
                }
            }

        }

        binding.btnFacebook.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)

        }
    }

    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit{ prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            //UUID: Universal Unique Identifier
            prefs[stringPreferencesKey("session")] = java.util.UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dispositivosmoviles@uce.edu.ec"
        }
    }

}