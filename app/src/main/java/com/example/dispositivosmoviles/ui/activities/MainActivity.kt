package com.example.dispositivosmoviles.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
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
import java.util.Locale
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

            val sn = Snackbar.make(
                binding.txtName,
                "",
                Snackbar.LENGTH_LONG
            )

            //contrato con las clausulas a ejecutar
            var message = when(resultActivity.resultCode) {
                RESULT_OK -> {
                    sn.setBackgroundTint(resources.getColor(R.color.blue))
                    resultActivity.data?.getStringExtra("result")
                        .orEmpty() //si no hay nada, devuelve vacio
                }
                RESULT_CANCELED -> {
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                    resultActivity.data?.getStringExtra("result")
                        .orEmpty()
                }
                else -> {
                    "Dudoso"
                }
            }
            sn.setText(message)
            sn.show()
        }

        val speechToText = registerForActivityResult(StartActivityForResult()){
            activityResult ->

            val sn = Snackbar.make(
                binding.txtName,
                "",
                Snackbar.LENGTH_LONG
            )

            var message = ""

            when(activityResult.resultCode){
                RESULT_OK -> {
                    val msg = activityResult.
                    data?.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                    )?.get(0).toString()

                    if(msg.isNotEmpty()){
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)
                    }
                }
                RESULT_CANCELED -> {
                    message = "Proceso cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }
                else -> {
                    "Ocurrio un error"
                    sn.setBackgroundTint(resources.getColor(R.color.red))
                }

            }
        }

        binding.btnFacebook.setOnClickListener {

            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM //modelo de lenguaje libre
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE, //en que idioma va a hblar
                Locale.getDefault() //toma el lenguaje del dispositivo
            )
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "Di algo..."
            )
            speechToText.launch(intentSpeech)

            /*val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)*/
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