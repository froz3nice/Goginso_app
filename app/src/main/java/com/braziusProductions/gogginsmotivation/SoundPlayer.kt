package com.braziusProductions.gogginsmotivation

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer

class SoundPlayer(val context: Activity, sound: Int) {
    fun playSound(soundRes: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, soundRes)
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    var mediaPlayer: MediaPlayer? = null

    init {
        mediaPlayer = MediaPlayer.create(context, sound)
    }
}