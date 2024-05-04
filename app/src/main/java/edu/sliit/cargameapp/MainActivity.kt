package edu.sliit.cargameapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GameTask {
    private lateinit var rootLayout: LinearLayout
    private lateinit var startBtn: Button
    private lateinit var mGameView: GameView
    private lateinit var score: TextView
    private lateinit var higherscore : TextView
    private var gameStarted = false
    private lateinit var sharedPreferences: SharedPreferences
    private var highScore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        mGameView = GameView(this, this)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("highScore", 0)
        higherscore = findViewById(R.id.higherscore)
        higherscore.text = "High Score: $highScore"


        startBtn.setOnClickListener {
            if (!gameStarted) {
                mGameView.setBackgroundResource(R.drawable.road)
                rootLayout.addView(mGameView)
                startBtn.visibility = View.GONE
                score.visibility = View.GONE
                gameStarted = true
            }
        }
    }

    override fun closeGame(mScore: Int) {
        if (gameStarted) {
            if (mScore > highScore) {
                highScore = mScore
                higherscore.text = "High Score: $highScore"
                // Update SharedPreferences with the new high score
                val editor = sharedPreferences.edit()
                editor.putInt("highScore", highScore)
                editor.apply()
            }
            higherscore.text = "High Score: $highScore"
            score.text = "Score : $mScore"
            rootLayout.removeView(mGameView)
            score.visibility = View.VISIBLE
            gameStarted = false // Reset the flag when the game is closed

            // Delay the start of the new activity
            Handler(Looper.getMainLooper()).postDelayed({
                // Start play Activity
                val intent = Intent(this, playActivity::class.java)
                intent.putExtra("score", mScore)
                startActivity(intent)
            }, 3000) // Delay for 3000 milliseconds (3 seconds)

        }
    }
}
