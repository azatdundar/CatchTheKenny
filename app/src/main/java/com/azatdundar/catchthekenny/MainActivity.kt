package com.azatdundar.catchthekenny

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.azatdundar.catchthekenny.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var score = 0
    var imageArray = arrayListOf<ImageView>()
    var randomNumber = Random.nextInt(0,9) // 9 is not included
    private lateinit var binding: ActivityMainBinding
    private lateinit var countDownTimer : CountDownTimer
    private lateinit var changeImageView: CountDownTimer
    private lateinit var sharedPref : SharedPreferences

    private var difficultyBasedImageInterval = 1000

    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val imageView1 = binding.image1
        val imageView2 = binding.image2
        val imageView3=  binding.image3
        val imageView4 = binding.image4
        val imageView5 = binding.image5
        val imageView6 = binding.image6
        val imageView7 = binding.image7
        val imageView8 = binding.image8
        val imageView9 = binding.image9

        sharedPref = getSharedPreferences("com.azatdundar.catchthekenny",Context.MODE_PRIVATE)

        val difficulty = sharedPref.getString("difficulty","easy") // default difficulty is medium

        if(difficulty.equals("easy")){
            difficultyBasedImageInterval = 1500
        }
        else if(difficulty.equals("medium")){
            difficultyBasedImageInterval = 800
        }

        else if(difficulty.equals("hard")){
            difficultyBasedImageInterval = 400
        }


        imageArray = arrayListOf<ImageView>(imageView1,imageView2,imageView3,imageView4,imageView5,imageView6, imageView7, imageView8, imageView9)

        changeImageView()




         countDownTimer = object : CountDownTimer(15000,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.text = getString(R.string.time_text, millisUntilFinished/1000)
            }

            override fun onFinish() {
                // Yes or no is gonna become here
                score = 0
                handler.removeCallbacks(runnable)
                makeImagesInvisible()
                showRestartDialog()

            }

        }.start()

         /*changeImageView = object : CountDownTimer(15000, difficultyBasedImageInterval.toLong()){

            override fun onTick(millisUntilFinished: Long) {
                makeImagesInvisible()

                randomNumber = Random.nextInt(0,9)
                imageArray[randomNumber].visibility = View.VISIBLE
            }
            override fun onFinish() {
                imageArray[randomNumber].visibility = View.VISIBLE
            }

        }.start()*/



    }

    fun countScore(view : View){
        score+= 1
        println(score)
        binding.scoreText.text = "Score: ${score}"

    }

    fun makeImagesInvisible(){
        for(image in imageArray){
            image.visibility = View.INVISIBLE
        }


    }

    fun changeImageView(){
        runnable = object : Runnable {
            override fun run() {
                makeImagesInvisible()


                val randomIndex = Random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,difficultyBasedImageInterval.toLong())
            }

        }

        handler.post(runnable)
    }

    fun showRestartDialog(){


        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setTitle("Game Over!")
        alertDialog.setMessage("Do you want to play again!")

        alertDialog.setPositiveButton("Yes"){dialog, id->

            binding.scoreText.text = "Score: ${score}"
            val intent = intent
            finish()
            startActivity(intent)


        }

        alertDialog.setNegativeButton("No"){dialog, id->
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }
        alertDialog.show()

    }

}