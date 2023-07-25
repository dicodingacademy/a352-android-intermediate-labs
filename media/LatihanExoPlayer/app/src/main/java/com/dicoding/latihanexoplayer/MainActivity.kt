package com.dicoding.latihanexoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.dicoding.latihanexoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val URL_VIDEO_DICODING =
            "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
        const val URL_AUDIO =
            "https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3"
    }

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initializePlayer()
        hideSystemUI()
    }

    private var player: ExoPlayer? = null

    private fun initializePlayer() {
        val videoItem = MediaItem.Builder().setUri(URL_VIDEO_DICODING).build()
        val audioItem = MediaItem.Builder().setUri(URL_AUDIO).build()

        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            viewBinding.playerView.player = exoPlayer
            exoPlayer.setMediaItem(videoItem)
            exoPlayer.addMediaItem(audioItem)
            exoPlayer.prepare()
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

//    public override fun onStart() {
//        super.onStart()
//        initializePlayer()
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        hideSystemUI()
//        if (player == null) {
//            initializePlayer()
//        }
//    }
//
//    public override fun onPause() {
//        super.onPause()
//        releasePlayer()
//    }
//
//    public override fun onStop() {
//        super.onStop()
//        releasePlayer()
//    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
