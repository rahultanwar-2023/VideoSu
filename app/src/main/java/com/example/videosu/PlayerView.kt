package com.example.videosu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import java.util.*
import java.util.Collections.shuffle

class PlayerView : YouTubeBaseActivity() {

    private val api_key = "AIzaSyAlJlFuGrTlGfidlH9zVxYPWyshJlj3RjI"

    lateinit var btn_play: Button
    lateinit var btn_suffle: Button
    lateinit var btn_stop: Button

    lateinit var btn_screen1: Button

    lateinit var ytPlayerView: YouTubePlayerView
    lateinit var initialize: YouTubePlayer.OnInitializedListener
    lateinit var initialize1: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_view)

        ytPlayerView = findViewById(R.id.yt_player)
        btn_suffle = findViewById(R.id.btn_shuffle)
        btn_stop = findViewById(R.id.btn_stop)
        btn_play = findViewById(R.id.btn_play)

        val videoList =
            mutableListOf("IEF6mw7eK4s", "8CEJoCr_9UI", "344u6uK9qeQ", "3-nM3yNi3wg", "nB7nGcW9TyE")
        val videoListWithOutError = mutableListOf(" ") + videoList + mutableListOf(" ")
        //Duplicate List for shuffle
        val duplicateVideoList =
            mutableListOf("IEF6mw7eK4s", "8CEJoCr_9UI", "344u6uK9qeQ", "3-nM3yNi3wg", "nB7nGcW9TyE")

        initialize = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                youTubePlayer: YouTubePlayer,
                b: Boolean
            ) {
                youTubePlayer.cueVideos(videoListWithOutError)
                youTubePlayer.play()
                btn_stop.setOnClickListener {
                    stopPlayer(youTubePlayer)
                }
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                youTubeInitializationResult: YouTubeInitializationResult
            ) {
                Log.d(
                    "onInitializationFailure",
                    "Initialization Failure" + youTubeInitializationResult.name
                )
            }
        }

        btn_play.setOnClickListener {
            ytPlayerView.initialize(api_key, initialize)
        }

        initialize1 = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                youTubePlayer: YouTubePlayer,
                b: Boolean
            ) {
                val shuffleList =
                    mutableListOf(" ") + shuffle(duplicateVideoList) + mutableListOf(" ")
                youTubePlayer.cueVideos(shuffleList)
                youTubePlayer.play()
                btn_stop.setOnClickListener {
                    stopPlayer(youTubePlayer)
                }

            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                youTubeInitializationResult: YouTubeInitializationResult
            ) {
                Log.d(
                    "onInitializationFailure",
                    "Initialization Failure" + youTubeInitializationResult.name
                )
            }
        }

        btn_suffle.setOnClickListener {
            ytPlayerView.initialize(api_key, initialize1)
        }

        btn_screen1 = findViewById(R.id.btn_Screen2)
        btn_screen1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun stopPlayer(youTubePlayer: YouTubePlayer) {
        try {
            youTubePlayer.pause()
            youTubePlayer.release()
        } catch (e: Exception) {
            Toast.makeText(
                applicationContext,
                "YouTube player already stop",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun <T : Comparable<T>> shuffle(items: MutableList<T>): List<T> {
        val rg: Random = Random()
        for (i in 0 until items.size) {
            val randomPosition = rg.nextInt(items.size)
            val tmp: T = items[i]
            items[i] = items[randomPosition]
            items[randomPosition] = tmp
        }
        return items
    }
}