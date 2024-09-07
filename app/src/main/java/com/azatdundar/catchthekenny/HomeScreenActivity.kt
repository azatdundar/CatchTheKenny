package com.azatdundar.catchthekenny

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.azatdundar.catchthekenny.databinding.ActivityHomeScreenBinding

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var intent : Intent
    private lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent = Intent(this, MainActivity::class.java)

        sharedPref = this.getSharedPreferences("com.azatdundar.catchthekenny",Context.MODE_PRIVATE)
    }

    fun playOnEasyMode(view : View){
        val difficultyLevel = sharedPref.edit().putString("difficulty","easy").apply()
        startActivity(intent)
    }

    fun playOnMediumMode(view : View){
        val difficultLevel = sharedPref.edit().putString("difficulty", "medium").apply()
        startActivity(intent)
    }

    fun playOnHardMode(view : View){
        val difficultyLevel = sharedPref.edit().putString("difficulty", "hard").apply()
        startActivity(intent)
    }


}