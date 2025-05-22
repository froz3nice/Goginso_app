package com.braziusProductions.gogginsmotivation

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

class ContinuousSoundPlayer(
    private val context: Context,
    @RawRes private var soundResourceId: Int
) {
    private var mediaPlayer: MediaPlayer? = null
    private var isPrepared = false
    private var currentPosition = 0
    private var completionListener: OnCompletionListener? = null

    interface OnCompletionListener {
        fun onCompletion()
    }

    init {
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(context, soundResourceId)
            mediaPlayer?.setOnCompletionListener {
                if (!mediaPlayer?.isLooping!!) {
                    // Only call the completion listener if not looping
                    completionListener?.onCompletion()
                }
            }
            isPrepared = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSoundResource(@RawRes soundResourceId: Int) {
        if (this.soundResourceId != soundResourceId) {
            this.soundResourceId = soundResourceId
            release()
            initMediaPlayer()
        }
    }

    fun playSound(loop: Boolean = false) {
        if (mediaPlayer == null) {
            initMediaPlayer()
        }

        try {
            mediaPlayer?.let {
                it.isLooping = loop
                if (currentPosition > 0) {
                    it.seekTo(currentPosition)
                    currentPosition = 0
                }
                if (isPrepared) {
                    it.start()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setOnCompletionListener(listener: OnCompletionListener) {
        this.completionListener = listener
    }

    fun pause() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    currentPosition = it.currentPosition
                    it.pause()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                    currentPosition = 0
                    isPrepared = false
                    // Reinitialize the media player
                    release()
                    initMediaPlayer()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun release() {
        try {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
                it.release()
                mediaPlayer = null
                isPrepared = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}