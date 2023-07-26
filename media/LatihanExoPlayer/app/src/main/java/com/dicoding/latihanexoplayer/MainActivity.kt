package com.dicoding.latihanexoplayer

import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import com.dicoding.latihanexoplayer.databinding.ActivityMainBinding
import com.google.common.util.concurrent.MoreExecutors

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val videoItem = MediaItem.Builder()
//            .setUri("https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4")
//            .build()
//        val audioItem = MediaItem.Builder()
//            .setUri("https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3")
//            .build()
//
//        val player = ExoPlayer.Builder(this).build().also { exoPlayer ->
//            exoPlayer.setMediaItem(videoItem)
//            exoPlayer.addMediaItem(audioItem)
//            exoPlayer.prepare()
//        }
//        binding.playerView.player = player

        hideSystemUI()
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
            val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
            controllerFuture.addListener(
                { binding.playerView.player = controllerFuture.get() },
                MoreExecutors.directExecutor()
            )
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
