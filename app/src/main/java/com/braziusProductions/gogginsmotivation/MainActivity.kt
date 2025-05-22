package com.braziusProductions.gogginsmotivation

import android.Manifest
import android.animation.AnimatorSet
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.braziusProductions.gogginsmotivation.databinding.ActivityMainBinding
import com.braziusProductions.gogginsmotivation.phrases.PhrasesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var animatorSet: AnimatorSet
    private var data = getPhrasesData().shuffled()
    private var selectedIndex = 0
    private lateinit var soundPlayer: ContinuousSoundPlayer
    private var isLoopEnabled = false
    private var isSequentialPlayback = true

    private val permissionsHelper by lazy {
        PermissionHelper(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    // Create a completion listener for the sound player
    private val soundCompletionListener = object : ContinuousSoundPlayer.OnCompletionListener {
        override fun onCompletion() {
            if (isSequentialPlayback && selectedIndex < data.size - 1) {
                // Move to next sound if sequential playback is enabled
                Handler(Looper.getMainLooper()).post {
                    // Automatically click next button
                    binding.next.performClick()
                }
            } else if (isSequentialPlayback) {
                // We've reached the end of the playlist
                updatePlayButtonState(false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBackgroundPager()
        soundPlayer = ContinuousSoundPlayer(this@MainActivity, data[0].soundRes)
        soundPlayer.setOnCompletionListener(soundCompletionListener)
        binding.bannerContainer.setOnClickListener {
            openPlayStore(this, "com.braziusProductions.calisthenicsworkouttracker")
        }
        binding.downloadButton.setOnClickListener {
            openPlayStore(this, "com.braziusProductions.calisthenicsworkouttracker")
        }

        initViews()
        setupOptionButtons()
    }

    private fun setupBackgroundPager() {
        binding.backgroundPager.adapter = BackgroundPagerAdapter()

        // Create dots indicator
        val dotsIndicator = binding.dotsIndicator
        val dots = Array(4) { ImageView(this) }

        dots.forEach { dot ->
            dot.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.dot_inactive)
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dotsIndicator.addView(dot, params)
        }
        // Set first dot as active
        dots[0].setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.dot_active)
        )

        // Update dots when page changes
        binding.backgroundPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    dots.forEachIndexed { index, dot ->
                        dot.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                if (index == position) R.drawable.dot_active
                                else R.drawable.dot_inactive
                            )
                        )
                    }
                }
            }
        )
    }

    fun openPlayStore(context: Context, packageName: String) {
        val uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            context.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            // Fallback to browser if Play Store not available
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }


    private fun checkPrevious() {
        binding.previous.isEnabled = selectedIndex != 0
        binding.previous.alpha = if (selectedIndex != 0) 1.0f else 0.5f
    }

    private fun checkNext() {
        binding.next.isEnabled = selectedIndex != data.size - 1
        binding.next.alpha = if (selectedIndex != data.size - 1) 1.0f else 0.5f
    }

    private fun initViews() {
        animatorSet = AnimatorSet()

        checkPrevious()
        checkNext()
        binding.mainText.text = data[selectedIndex].phrase

        binding.next.setOnClickListener {
            if (selectedIndex < data.size - 1) selectedIndex++
            checkNext()
            checkPrevious()
            slide(true)

            // If sound is currently playing or sequential playback is active, update to new sound
            if (soundPlayer.isPlaying() || isSequentialPlayback) {
                soundPlayer.stop()
                soundPlayer.setSoundResource(data[selectedIndex].soundRes)
                soundPlayer.playSound(isLoopEnabled)
                updatePlayButtonState(true)
            }
        }

        binding.previous.setOnClickListener {
            if (selectedIndex > 0) selectedIndex--
            checkPrevious()
            checkNext()
            slide(false)

            // If sound is currently playing or sequential playback is active, update to new sound
            if (soundPlayer.isPlaying() || isSequentialPlayback) {
                soundPlayer.stop()
                soundPlayer.setSoundResource(data[selectedIndex].soundRes)
                soundPlayer.playSound(isLoopEnabled)
                updatePlayButtonState(true)
            }
        }

        binding.play.setOnClickListener {
            togglePlayback()
        }

        // Add long click listener for sequential playback
        binding.play.setOnLongClickListener {
            toggleSequentialPlayback()
            true
        }

        // Initial states
        updatePlayButtonState(false)
    }

    private fun togglePlayback() {
        if (!soundPlayer.isPlaying()) {
            soundPlayer.setSoundResource(data[selectedIndex].soundRes)
            soundPlayer.playSound(isLoopEnabled)
            updatePlayButtonState(true)
        } else {
            soundPlayer.pause()
            updatePlayButtonState(false)
        }
    }

    private fun toggleSequentialPlayback() {

        if (isSequentialPlayback) {
            // Start sequential playback from current index
            isLoopEnabled = false // Disable loop to allow moving to next sound
            soundPlayer.stop() // Stop current playback if any
            soundPlayer.setSoundResource(data[selectedIndex].soundRes)
            soundPlayer.playSound(false) // Start playing without loop
            updatePlayButtonState(true)
            Toast.makeText(this, "Sequential playback started", Toast.LENGTH_SHORT).show()
        } else {
            // Stop sequential playback
            soundPlayer.pause()
            updatePlayButtonState(false)
            Toast.makeText(this, "Sequential playback stopped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePlayButtonState(isPlaying: Boolean) {
        val playButton = binding.play
        if (isPlaying) {
            playButton.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_pause_circle_24
                )
            )
        } else {
            playButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play))
        }
    }

    private fun setupOptionButtons() {
        binding.rateApp.setOnClickListener {
            rateApp()
        }

        binding.showAll.setOnClickListener {
            showAllPhrases()
        }

        binding.shareSound.setOnClickListener {
            shareSound(this@MainActivity, data[selectedIndex].soundRes)
        }

        binding.downloadImage.setOnClickListener {
            downloadImage()
        }
    }

    private fun slide(right: Boolean) {
        // Setup slide in animation
        val slideInAnimation = if (right) {
            AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        } else {
            AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        }

        // Setup slide out animation
        val slideOutAnimation = if (right) {
            AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
        } else {
            AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        }

        // Apply animations
        binding.mainText.startAnimation(slideOutAnimation)

        // Update text after animation
        slideOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.mainText.text = data[selectedIndex].phrase
                binding.mainText.startAnimation(slideInAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    override fun onStop() {
        super.onStop()
        // Do not stop the sound player here to allow background playback
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up resources when destroying the activity
        soundPlayer.release()
    }

    private fun hideViews(hide: Boolean) {
        val visibility = if (hide) View.GONE else View.VISIBLE
        binding.controlsCard.visibility = visibility
        binding.optionsCard.visibility = visibility
        binding.bannerContainer.visibility = visibility
        binding.dotsIndicator.visibility = visibility
    }

    private fun saveImg() {
        hideViews(true)
        Handler(Looper.getMainLooper()).postDelayed({
            takeScreenshot(binding.rootView, window) { bmp ->
                val uri = bmp.saveImage(this@MainActivity)
                hideViews(false)
                Toast.makeText(this, "Quote saved to gallery", Toast.LENGTH_SHORT).show()
            }
        }, 200)
    }

    private fun downloadImage() {
        saveImg()
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
        startActivity(Intent(this, PhrasesActivity::class.java))
    }

    private fun rateApp() {
        openAppInPlayStore()
    }

    private fun openAppInPlayStore() {
        val uri = Uri.parse("market://details?id=$packageName")
        val goToMarketIntent = Intent(Intent.ACTION_VIEW, uri)

        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= 21) {
            flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        goToMarketIntent.addFlags(flags)

        try {
            startActivity(goToMarketIntent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            )
            startActivity(intent)
        }
    }
}