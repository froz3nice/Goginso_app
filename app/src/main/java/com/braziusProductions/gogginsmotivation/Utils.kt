package com.braziusProductions.gogginsmotivation

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.PixelCopy
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.app.ShareCompat
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.ArrayList
import android.os.Environment
import androidx.annotation.RawRes
import androidx.core.content.FileProvider
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun getPhrasesData(): ArrayList<PhraseData> {
    val withCurses = false
    val fucking: String
    val shit: String
    val fuck: String
    val mofo: String

    if (withCurses) {
        fucking = "fucking"
        shit = "shit"
        fuck = "fuck"
        mofo = "motherfucker"
    } else {
        fucking = "f-ing"
        shit = "sh"
        fuck = "f"
        mofo = "mfer"
    }
    return ArrayList<PhraseData>().apply {
        add(
            PhraseData(
                R.raw.always_have_haters_embrace,
                "One thing in life, you are gonna always have haters. Embrace them."
            )
        )
        add(
            PhraseData(
                R.raw.at_the_bottom_was_me, "At the bottom of insecurities," +
                        " fear, self doubt, lies, was me burried in a $fucking fear position."
            )
        )
        add(
            PhraseData(
                R.raw.bcs_it_sucks_merry_christmas,
                "Pain is just a feeling. You see this happiness?" +
                        " Why, because it sucks. That's why I'm so happy right now. Merry Christmas."
            )
        )
        add(
            PhraseData(
                R.raw.be_better_doctor,
                "It's not enough you became a doctor. Be a better doctor!"
            )
        )
        add(
            PhraseData(
                R.raw.be_in_love_w_yourself_face_different,
                "Be in love with yourself. Write that $shit down. Start to face these" +
                        " different things in life."
            )
        )
        add(PhraseData(R.raw.be_mvp, "Be the $fucking MVP!"))
        add(
            PhraseData(
                R.raw.be_who_the_f_u_are, "Don't worry about that $shit, be unapologetic," +
                        " get after it, stay hard, be what the $fuck you are."
            )
        )
        add(
            PhraseData(
                R.raw.big_race_more_miles, "If you training for a nice big race," +
                        " get your legs all tired, mugged up, jacked up," +
                        " when your mind and body says you don't want to $fucking do $shit anymore." +
                        " And guess what you do, you get up and get some more miles in"
            )
        )
        add(
            PhraseData(
                R.raw.cannot_fix_until_fix_urself,
                "You cannot fix anything in life until you fix yourself"
            )
        )
        add(
            PhraseData(
                R.raw.do_it_do_it_crazy, "Before you say something's impossible," +
                        " do it and do it to a level that people think that you might be $fucking crazy"
            )
        )
        add(PhraseData(R.raw.do_smth_everyday_that_suck, "Do something every day that sucks"))
        add(
            PhraseData(
                R.raw.dont_have_quiting_mind,
                "Don't have a $fucking quitting mind, repetition every day, stay hard!"
            )
        )
        add(
            PhraseData(
                R.raw.equal_playing_field_get_in_the_game,
                "We got to get on an equal playing field, find a way to get" +
                        " in an equal playing field with somebody. Get in the game!"
            )
        )
        add(
            PhraseData(
                R.raw.figure_out_yourselft,
                "Figure out yourself, get yourself hard, stay hard."
            )
        )
        add(
            PhraseData(
                R.raw.find_best_self_when_least_motivated,
                "You have to find your best self when you are the least motivated"
            )
        )
        add(
            PhraseData(
                R.raw.find_throw_towel_back, "A lot of us can't do thing on their own." +
                        " So find somebody in your life so when you throw that towel in, they throw that $mofo back at you and say you are not $fucking" +
                        " done yet. Stay true to yourself, stay hard."
            )
        )
        add(
            PhraseData(
                R.raw.fragile_die_w_meaning, "We are all very fragile in" +
                        " this world. We have to know when we die, we die with some meaning"
            )
        )
        add(
            PhraseData(
                R.raw.get_out_there_triple_down_on_weakness,
                "Do something every day that sucks. Get outside your" +
                        " comfort zone. Triple down on your weaknesses."
            )
        )
        add(
            PhraseData(
                R.raw.get_to_the_source_and_fix_prob, "We like to live in social media," +
                        " with lies about ourselves, how great we are. Get to the source and fix the problem!"
            )
        )
        add(
            PhraseData(
                R.raw.good_way_hit_the_gym, "A good way to build that" +
                        " $fucking self esteem up is get to the gym and change the way you look."
            )
        )
        add(
            PhraseData(
                R.raw.hate_get_better_like_same,
                "I'd rather you hate me and get better, than like me and stay the same!"
            )
        )
        add(PhraseData(R.raw.healthy_body_healthy_mind, "A healthy body use your healthy mind"))
        add(
            PhraseData(
                R.raw.hungry_not_civilised,
                "It's important to always stay hungry and never get too civilized"
            )
        )
        add(
            PhraseData(
                R.raw.if_u_lose_life_in_head,
                "If you lose, it's because you allow life to get in your $fucking head"
            )
        )
        add(
            PhraseData(
                R.raw.internal_motivation,
                "What do you do, when you gave to external motivation? It's about the internal."
            )
        )
        add(
            PhraseData(
                R.raw.judgeing_problems_themselves, "Everybody else out there," +
                        " everybody judging you, they also have problems themselves"
            )
        )
        add(
            PhraseData(
                R.raw.learn_brain_like_brain_learned_you,
                "You got to learn your brain, like the brain has learned you"
            )
        )
        add(
            PhraseData(
                R.raw.life_brutal_love_about_it, "Life is the most brutal endurance sport" +
                        " of all time. And that's what we have to love about it"
            )
        )
        add(
            PhraseData(
                R.raw.lifes_hate_game_play_w_urself,
                "Life is one big $fucking hate game. You're playing with yourself!"
            )
        )
        add(
            PhraseData(
                R.raw.not_enough_50pounds, "It's not enough you lost 50 pounds, got out there and" +
                        " do something with it!"
            )
        )
        add(PhraseData(R.raw.one_day_but_not_today, "How about days off? One day but not today."))
        add(
            PhraseData(
                R.raw.one_finish_death, "There's only one finish line in life. " +
                        "And that is death."
            )
        )
        add(
            PhraseData(
                R.raw.people_dont_like_you_doing_right,
                "Embrace the fact that people don't like you." +
                        " It means you're doing something right."
            )
        )
        add(
            PhraseData(
                R.raw.people_questioning_who_you_are,
                "That's exactly where you need to $fucking be" +
                        " in your life with those people who put you in the world of questioning who you are."
            )
        )
        add(
            PhraseData(
                R.raw.positive_self_talk_doesnt_work,
                "Positive self talk doesn't work unless you put the work behind it." +
                        " It's only bullshit. Put the work behind your positive self talk."
            )
        )
        add(
            PhraseData(
                R.raw.show_up_everyday,
                "You have to have the mentality to show up every day in your life." +
                        " No matter what life throws at you."
            )
        )
        add(
            PhraseData(
                R.raw.shut_the_fcking_noise_out,
                "Learn one thing. Shut the $fucking noise out!"
            )
        )
        add(
            PhraseData(
                R.raw.space_in_your_head_losing,
                "If you are allowing people, things and situations " +
                        "to own space in your $fucking head, you are losing."
            )
        )
        add(PhraseData(R.raw.stay_hard, "Stay hard"))
        add(
            PhraseData(
                R.raw.stay_in_your_own_mind,
                "Stay in your own mind. Don't let them own yours."
            )
        )
        add(
            PhraseData(
                R.raw.stop_hero_of_your_story, "Stop looking at me or other people out here to be" +
                        " the hero of your story."
            )
        )
        add(
            PhraseData(
                R.raw.stop_telin_urself_you_doin_enough,
                "You have to do more. You have to stop telling yourself" +
                        " that you are doing enough"
            )
        )
        add(
            PhraseData(
                R.raw.the_best_place_isolation,
                "The best place to make those dreams come true is isolation"
            )
        )
        add(
            PhraseData(
                R.raw.the_grind_is_forever, "The grind is forever. There is no end. There" +
                        " is no count down. As long" + " as you're breathing, you have to get trying to get better."
            )
        )
        add(
            PhraseData(
                R.raw.this_time_to_get_to_know_you_person,
                "This is time for you right now in all that " +
                        "solitude to get to know who you are as a person."
            )
        )
        add(
            PhraseData(
                R.raw.ture_growth_finding_yourself,
                "That's where true growth is. It's finding yourself."
            )
        )
        add(
            PhraseData(
                R.raw.when_u_were_younger_what_u_wannabe,
                "Let me ask you a question. When you were younger" +
                        " what did you want to be?"
            )
        )
        add(PhraseData(R.raw.whos_gonna_carry_the_boats, "Who's gonna carry the boats?!"))
        add(PhraseData(R.raw.you_dont_know_me_son, "You don't know me son!"))
        add(
            PhraseData(
                R.raw.fail_test_get_over,
                "You fail the test in school, you worked your fcing as off. Get over it."
            )
        )
        add(
            PhraseData(
                R.raw.insecure_flush_it_out,
                "All these insecure people putting those insecurities on you. You got to flush it out."
            )
        )
        add(
            PhraseData(
                R.raw.morning_care_yourself,
                "The main purpose in life is you. If you wake up in the morning and you don't want to do something, you don't care enough about yourself."
            )
        )
        add(
            PhraseData(
                R.raw.self_disciple_everything,
                "Self discipline is everything"
            )
        )
        add(
            PhraseData(
                R.raw.spark_but_go_inside,
                "I may give you the spark, but you got to go inside yourself to find it."
            )
        )
        add(
            PhraseData(
                R.raw.sucks_continue_to_suck,
                "It sucks. It just f-ing sucks. And it's going to continue to suck"
            )
        )
        add(
            PhraseData(
                R.raw.until_you_know_stand_for_anything,
                "Until you know what you want to stand for, you'll always just be sitting down. You'll never stand for anything."
            )
        )
        add(
            PhraseData(
                R.raw.every_mfker_aint_do,
                "Every mfker ain't gonna do what i'm gonna do. This is how you level up."
            )
        )
        add(
            PhraseData(
                R.raw.very_purpose_is_you,
                "Very purpose is you. You are always the purpose."
            )
        )
        add(
            PhraseData(
                R.raw.purpose_always_there,
                "The purpose is always there. The purpose never leaves us."
            )
        )
        add(
            PhraseData(
                R.raw.if_not_performing_not_ready,
                "If you're not constantly performing without purpose, you're not going to be ready when the time comes."
            )
        )
    }
}


val displayMetrics: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }

val screenRectPx: Rect
    get() = displayMetrics.run { Rect(0, 0, widthPixels, heightPixels) }

fun Int.resToUri(context: Activity): Uri {
    return Uri.parse("android.resource://" + context.packageName + "/" + this)
}

fun shareSound(context: Context, @RawRes soundResId: Int, fileName: String = "goggins.mp3") {
    // Step 1: Copy raw resource to cache
    val cacheFile = File(context.cacheDir, fileName)
    context.resources.openRawResource(soundResId).use { input ->
        FileOutputStream(cacheFile).use { output ->
            input.copyTo(output)
        }
    }

    // Step 2: Get content URI using FileProvider
    val uri = FileProvider.getUriForFile(
        context,
        "com.braziusProductions.gogginsmotivation.fileprovider",
        cacheFile
    )

    // Step 3: Create and send share intent
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "audio/mp3"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share Sound"))
}

fun takeScreenshot(view: View, window: Window?, bitmap: (Bitmap) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        if (window != null) {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(
                    window,
                    Rect(
                        locationOfViewInWindow[0],
                        locationOfViewInWindow[1],
                        locationOfViewInWindow[0] + view.width,
                        locationOfViewInWindow[1] + view.height
                    ),
                    bitmap,
                    { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            bitmap(bitmap)
                        }
                        // possible to handle other result codes ...
                    },
                    Handler(Looper.getMainLooper())
                )
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
            }
        }
    } else {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache(true)
        val b = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        bitmap(b)
    }
}
fun Context.toast(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
fun openAndroidPermissionsMenu(context: Activity) {
    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
    intent.data = Uri.parse("package:" + context.getPackageName())
    context.startActivity(intent)
}

fun openAlarmSoundPicker(context: Activity) {
    val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
        putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone")
        putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM))
    }
    context.startActivityForResult(intent, 999) // Handle result in onActivityResult
}

fun copyMp3ToAlarms(context: Context, @RawRes rawId: Int, fileName: String): Uri? {
    val alarmsDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS), fileName)
    if (!alarmsDir.exists()) {
        context.resources.openRawResource(rawId).use { input ->
            FileOutputStream(alarmsDir).use { output ->
                input.copyTo(output)
            }
        }
    }

    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DATA, alarmsDir.absolutePath)
        put(MediaStore.MediaColumns.TITLE, fileName.removeSuffix(".mp3"))
        put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
        put(MediaStore.Audio.Media.IS_RINGTONE, false)
        put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
        put(MediaStore.Audio.Media.IS_ALARM, true)
        put(MediaStore.Audio.Media.IS_MUSIC, false)
    }

    val uri = MediaStore.Audio.Media.getContentUriForPath(alarmsDir.absolutePath)
    return context.contentResolver.insert(uri!!, values)
}


private fun setRingtone(k: File, type: Int, context: Activity): Boolean {
    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.TITLE, k.name)
        put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
        put(MediaStore.MediaColumns.SIZE, k.length())
        when (type) {
            RingtoneManager.TYPE_RINGTONE -> put(MediaStore.Audio.Media.IS_RINGTONE, true)
            RingtoneManager.TYPE_ALARM -> put(MediaStore.Audio.Media.IS_ALARM, true)
        }
    }

    return try {
        val newUri: Uri? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)?.also { uri ->
                context.contentResolver.openOutputStream(uri)?.use { os ->
                    FileInputStream(k).use { input ->
                        input.copyTo(os)
                    }
                }
            }
        } else {
            values.put(MediaStore.MediaColumns.DATA, k.absolutePath)
            val uri = MediaStore.Audio.Media.getContentUriForPath(k.absolutePath)!!
            context.contentResolver.delete(uri, MediaStore.MediaColumns.DATA + "=?", arrayOf(k.absolutePath))
            context.contentResolver.insert(uri, values)
        }

        newUri?.let {
            RingtoneManager.setActualDefaultRingtoneUri(context, type, it)
            true
        } ?: false
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun setRingtoneOrAlarm(k: File, type: Int, context: Activity, success: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Settings.System.canWrite(context)) {
            if (setRingtone(k, type, context)) {
                success()
            }
        } else {
            openAndroidPermissionsMenu(context)
        }
    } else {
        if (setRingtone(k, type, context)) {
            success()
        }
    }
}

fun setAlarmSound(context: Context, soundResId: Int, fileName: String = "goggins.mp3") {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_RINGTONES)
        put(MediaStore.Audio.Media.IS_ALARM, 1)
        put(MediaStore.Audio.Media.IS_MUSIC, 0)
    }

    val resolver = context.contentResolver
    val audioUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

    val newUri = resolver.insert(audioUri, contentValues)

    if (newUri != null) {
        // Copy the sound from res/raw to the MediaStore location
        val inputStream = context.resources.openRawResource(soundResId)
        val outputStream: OutputStream? = resolver.openOutputStream(newUri)

        outputStream?.use { out ->
            inputStream.copyTo(out)
        }

        // Set as default alarm
        RingtoneManager.setActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_ALARM,
            newUri
        )
    }
}

