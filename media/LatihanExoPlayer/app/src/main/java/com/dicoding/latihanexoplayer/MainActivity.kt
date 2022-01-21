package com.dicoding.latihanexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.dicoding.latihanexoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {
    companion object {
        const val URL_VIDEO_DICODING =
            "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
        const val URL_AUDIO =
            "https://cdn.pixabay.com/download/audio/2022/01/18/audio_d0a13f69d2.mp3?filename=indie-folk-king-around-here-15045.mp3"
    }

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    private var player: ExoPlayer? = null

    private fun initializePlayer() {
        val mediaItem = MediaItem.fromUri(MainActivity.URL_VIDEO_DICODING)
        val anotherMediaItem = MediaItem.fromUri(MainActivity.URL_AUDIO)

        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.videoView.player = exoPlayer
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.addMediaItem(anotherMediaItem)
            exoPlayer.prepare()
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (Util.SDK_INT <= 23 && player == null) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
