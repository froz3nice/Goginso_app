package com.braziusProductions.gogginsmotivation.phrases

import android.media.RingtoneManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.braziusProductions.gogginsmotivation.*
import kotlinx.android.synthetic.main.activity_phrases.*


class PhrasesActivity : AppCompatActivity() {
    private var soundPlayer: SoundPlayer? = null
    private lateinit var viewAdapter: PhrasesAdapter

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrases)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "All Phrases"
        val data = getPhrasesData()
        soundPlayer = SoundPlayer(this, data[0].soundRes)

        viewAdapter = PhrasesAdapter(data) { phrase, type ->
            when (type) {
                PhraseEnum.SHARE ->  shareSound(this@PhrasesActivity,phrase.soundRes)
                PhraseEnum.RINGTONE -> saveMp3File(phrase.soundRes,this@PhrasesActivity){
                    setRingtoneOrAlarm(it,RingtoneManager.TYPE_RINGTONE,this@PhrasesActivity){
                        toast("Ringtone set!")
                    }
                }
                PhraseEnum.PLAY -> soundPlayer?.playSound(phrase.soundRes)
                PhraseEnum.ALARM -> saveMp3File(phrase.soundRes,this@PhrasesActivity){
                    setRingtoneOrAlarm(it,RingtoneManager.TYPE_ALARM,this@PhrasesActivity){
                        toast("Alarm sound set!")
                    }
                }
            }

        }

        recycler_view.apply{
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@PhrasesActivity)

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    override fun onStop() {
        super.onStop()
        soundPlayer?.stop()
    }
}