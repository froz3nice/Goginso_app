package com.braziusProductions.gogginsmotivation.phrases

import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.braziusProductions.gogginsmotivation.SoundPlayer
import com.braziusProductions.gogginsmotivation.copyMp3ToAlarms
import com.braziusProductions.gogginsmotivation.databinding.ActivityPhrasesBinding
import com.braziusProductions.gogginsmotivation.getPhrasesData
import com.braziusProductions.gogginsmotivation.openAlarmSoundPicker
import com.braziusProductions.gogginsmotivation.openAndroidPermissionsMenu
import com.braziusProductions.gogginsmotivation.saveMp3File
import com.braziusProductions.gogginsmotivation.setAlarmSound
import com.braziusProductions.gogginsmotivation.setRingtoneOrAlarm
import com.braziusProductions.gogginsmotivation.shareSound
import com.braziusProductions.gogginsmotivation.toast

class PhrasesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhrasesBinding
    private var soundPlayer: SoundPlayer? = null
    private lateinit var viewAdapter: PhrasesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhrasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "All Phrases"

        val data = getPhrasesData()
        if (data.isNotEmpty()) {
            soundPlayer = SoundPlayer(this, data[0].soundRes)
        }

        viewAdapter = PhrasesAdapter(data) { phrase, type ->
            when (type) {
                PhraseEnum.SHARE -> {
                    shareSound(this, phrase.soundRes)
                }

                PhraseEnum.RINGTONE -> {
                    saveMp3File(phrase.soundRes, this) {
                        setRingtoneOrAlarm(it, RingtoneManager.TYPE_RINGTONE, this) {
                            toast("Ringtone set!")
                        }
                    }
                }

                PhraseEnum.PLAY -> {
                    soundPlayer?.playSound(phrase.soundRes)
                }

                PhraseEnum.ALARM -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.System.canWrite(this)) {
                            setAlarmSound(this, phrase.soundRes)
                            toast("go to alarm settings and set goggins.mp3 as alarm sound")
                        } else {
                            openAndroidPermissionsMenu(this)
                        }
                    } else {
                        setAlarmSound(this, phrase.soundRes)
                    }
                }
            }
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PhrasesActivity)
            adapter = viewAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        soundPlayer?.stop()
    }
}
