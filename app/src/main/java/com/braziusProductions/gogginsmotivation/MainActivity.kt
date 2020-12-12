package com.braziusProductions.gogginsmotivation

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorSet
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.braziusProductions.gogginsmotivation.BottomSheetItem.*
import com.braziusProductions.gogginsmotivation.phrases.PhrasesActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(), OptionsBottomSheetFragment.ItemClickListener {
    private lateinit var animatorSet: AnimatorSet
    var data = getPhrasesData()
    var selectedIndex = 0
    lateinit var soundPlayer: SoundPlayer
    val permissionsHelper by lazy {
        PermissionHelper(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        data.shuffle()
        soundPlayer = SoundPlayer(this@MainActivity,data[0].soundRes)
        initViews()

        features.setOnClickListener {
            supportFragmentManager.let {
                OptionsBottomSheetFragment.newInstance(Bundle()).apply {
                    mListener = this@MainActivity
                    show(it, tag)
                }
            }
        }
    }

    private fun checkPrevious() {
        if (selectedIndex == 0) previous.isEnabled = false
    }

    private fun checkNext() {
        if (selectedIndex == data.size - 1) next.isEnabled = false
    }

    private fun initViews() {
        animatorSet = AnimatorSet()

        checkPrevious()
        main_text.text = data[selectedIndex].phrase

        next.setOnClickListener {
            if (selectedIndex < data.size - 1) selectedIndex++
            checkNext()
            previous.isEnabled = true
            slide(true)
        }

        previous.setOnClickListener {
            if (selectedIndex > 0) selectedIndex--
            checkPrevious()
            next.isEnabled = true
            slide(false)
        }

        play.setOnClickListener {
            soundPlayer.playSound(data[selectedIndex].soundRes)
        }
    }

    fun slide(right: Boolean) {
        main_text.text = data[selectedIndex].phrase
        val pos = if (right) screenRectPx.right.toFloat() else -screenRectPx.right.toFloat()
        //main_text2.animate().x(pos).setDuration(0).start()
    }

    override fun onStop() {
        super.onStop()
        soundPlayer.stop()
    }

    override fun onItemClick(item: BottomSheetItem) {

        when (item) {
            RATE -> rateApp()
            SHOW_ALL -> showAllPhrases()
            SHARE_SOUND -> shareSound(this@MainActivity,data[selectedIndex].soundRes)
            DOWNLOAD_IMAGE -> downloadImage()
        }
    }

    fun hideViews(hide: Boolean) {
        next.visibility = if (hide) View.GONE else View.VISIBLE
        previous.visibility = if (hide) View.GONE else View.VISIBLE
        play.visibility = if (hide) View.GONE else View.VISIBLE
        features.visibility = if (hide) View.GONE else View.VISIBLE
    }

    fun saveImg() {
        hideViews(true)
        Handler(Looper.getMainLooper()).postDelayed({
            takeScreenshot(root_view, window) { bmp ->
                val uri = bmp.saveImage(this@MainActivity)
                hideViews(false)
                Toast.makeText(this, "photo saved here $uri", Toast.LENGTH_SHORT).show()
            }
        }, 200)
    }

    private fun downloadImage() {
        if (permissionsHelper.isGranted()) {
            saveImg()
        } else permissionsHelper.request()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsHelper.onRequestPermissionResult(requestCode, grantResults) {
            onGranted {
                saveImg()
            }
            onDenied { isPermanent ->
                // User have denied permission request
                if (isPermanent) {
                    // This is the end, "Never ask again" has been checked.
                    // It's time to show dialog, which directs user to application settings system page.
                    permissionsHelper.showApplicationDetailsSettings()
                }
            }
        }
    }

    private fun showAllPhrases() {
        startActivity(Intent(this,PhrasesActivity::class.java))
    }

    private fun rateApp() {
        openAppInPlayStore()
    }

    fun openAppInPlayStore() {
        val uri = Uri.parse("market://details?id=" + packageName)
        val goToMarketIntent = Intent(Intent.ACTION_VIEW, uri)

        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= 21) {
            flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        goToMarketIntent.addFlags(flags)

        try {
            startActivity( goToMarketIntent, null)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + packageName))

            startActivity(intent, null)
        }
    }

}