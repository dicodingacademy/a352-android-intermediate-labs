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
        const val URL_VIDEO_DICODING = "https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4"
    }

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val player = ExoPlayer.Builder(this).build()
        viewBinding.videoView.player = player

        val mediaItem = MediaItem.fromUri(URL_VIDEO_DICODING)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

//    public override fun onStart() {
//        super.onStart()
//        if (Util.SDK_INT > 23) {
//            initializePlayer()
//        }
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        if (Util.SDK_INT <= 23 || player == null) {
//            initializePlayer()
//        }
//    }
//
//    public override fun onPause() {
//        super.onPause()
//        if (Util.SDK_INT <= 23) {
//            releasePlayer()
//        }
//    }
//
//    public override fun onStop() {
//        super.onStop()
//        if (Util.SDK_INT > 23) {
//            releasePlayer()
//        }
//    }
//
//    private fun initializePlayer() {
//        player = ExoPlayer.Builder(this)
//            .build()
//            .also { exoPlayer ->
//                viewBinding.videoView.player = exoPlayer
//
//                val mediaItem = MediaItem.fromUri(URL_VIDEO_DICODING)
//                exoPlayer.setMediaItem(mediaItem)
////                exoPlayer.playWhenReady = false
////                exoPlayer.seekTo(currentWindow, playbackPosition)
//                exoPlayer.prepare()
//            }
//    }
//
//    private fun releasePlayer() {
//        player?.release()
//        player = null
//    }
}
