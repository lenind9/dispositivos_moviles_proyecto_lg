package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityQuizBinding
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var binding : ActivityQuizBinding
    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var currentCharacter: MarvelChars

    private lateinit var timer: CountDownTimer
    private var score = 0
    private var tiempoRestante = 60000L // 1 minuto en milisegundos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupCharacterNames()

        val characterNames = listOf("Iron Man", "Spider-Man", "Captain America", "Thor", "Hulk", "Black Widow")

        timer = object : CountDownTimer(tiempoRestante, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished
                binding.textTimer.text = "Tiempo restante: ${millisUntilFinished / 1000} segundos"
            }

            override fun onFinish() {
                showResult()
            }
        }

        // Inicia el juego
        /*startNewRound(characterNames)
        binding.textTimer.text = "Tiempo restante: 60 segundos"*/

        // Configura los botones
        binding.btnOpcion1.setOnClickListener { checkAnswer(binding.btnOpcion1.text.toString()) }
        binding.btnOpcion2.setOnClickListener { checkAnswer(binding.btnOpcion2.text.toString()) }
        binding.btnOpcion3.setOnClickListener { checkAnswer(binding.btnOpcion3.text.toString()) }

        // Inicia el temporizador al tocar el botón "Comenzar juego"
        binding.btnComenzar.setOnClickListener {
            timer.start()
            binding.btnComenzar.visibility = View.GONE
            //startNewRound()
        }

    }

    private fun chargeDataRVAPI(limit: Int, offset:Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            //rvAdapter.items = JikanAnimeLogic().getAllAnimes()
            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(
                    offset, limit
                ))
            }

            //offset = offset+ limit;
        }

    }

    private fun startNewRound(characterNames: List<String>) {
        val randomIndex = Random.nextInt(characterNames.size)
        val correctName = characterNames[randomIndex]
        //currentCharacter = MarvelChars(randomIndex, correctName) // Supongamos que tienes una clase MarvelCharacter

        val shuffledNames = characterNames.shuffled()
        binding.btnOpcion1.text = shuffledNames[0]
        binding.btnOpcion2.text = shuffledNames[1]
        binding.btnOpcion3.text = shuffledNames[2]

        // Muestra la imagen del personaje (implementación según tu modelo de datos)
        //binding.imgCharacter.setImageResource(currentCharacter.imageResource)
    }

    private fun checkAnswer(selectedName: String) {
        if (selectedName == currentCharacter.name) {
            score++
        } else {
            score--
        }

        //startNewRound(characterNames) // Inicia un nuevo turno con nuevos nombres de personajes
    }

    private fun showResult() {
        val resultMessage = "Juego terminado. Puntaje final: $score"
        binding.textScore.text = resultMessage
        binding.textScore.visibility = View.VISIBLE
        binding.textTimer.visibility = View.GONE
        binding.buttonsLayout.visibility = View.GONE
    }
}