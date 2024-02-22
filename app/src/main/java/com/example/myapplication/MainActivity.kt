package com.example.myapplication

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var playPauseButton: Button
    private lateinit var randomButton: Button
    private lateinit var songNameTextView: TextView
    private lateinit var songCountTextView: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    private var currentSongIndex = 0
    private var currentPosition = 0

    // Lista de recursos en la carpeta raw
    private val SONGS_RAW_IDS = intArrayOf(
        R.raw.entredosmares,
        R.raw.madbirds,
        R.raw.niggersinparis
        // Agrega más canciones según sea necesario
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playPauseButton = findViewById(R.id.playPauseButton)
        randomButton = findViewById(R.id.randomButton)
        songNameTextView = findViewById(R.id.songNameTextView)
        songCountTextView = findViewById(R.id.songCountTextView)

        mediaPlayer = MediaPlayer()

        randomButton.setOnClickListener {
            playRandomSong()
        }

        playPauseButton.setOnClickListener {
            togglePlayPause()
        }

        // Oculta el botón de play/pausa inicialmente
        playPauseButton.visibility = View.GONE

        // Actualiza el número de canciones en el TextView
        songCountTextView.text = "Número de canciones: ${SONGS_RAW_IDS.size}"
    }

    private fun playRandomSong() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            isPlaying = false
            updatePlayPauseButton()
        }

        currentSongIndex = Random().nextInt(SONGS_RAW_IDS.size)
        val selectedSongId = SONGS_RAW_IDS[currentSongIndex]

        val songName = "Canción ${currentSongIndex + 1}"
        songNameTextView.text = songName

        mediaPlayer = MediaPlayer.create(this, selectedSongId)
        mediaPlayer.start()
        isPlaying = true
        updatePlayPauseButton()

        // Muestra el botón de play/pausa después de presionar el botón de reproducir aleatorio
        playPauseButton.visibility = View.VISIBLE
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            mediaPlayer.pause()
            currentPosition = mediaPlayer.currentPosition
        } else {
            mediaPlayer.start()
        }
        isPlaying = !isPlaying
        updatePlayPauseButton()
    }

    private fun updatePlayPauseButton() {
        playPauseButton.text = if (isPlaying) "Pausa" else "Play"
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}