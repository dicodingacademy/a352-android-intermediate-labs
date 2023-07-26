package com.dicoding.latihanexoplayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

@RequiresApi(Build.VERSION_CODES.Q)
class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        initializeSessionAndPlayer()
    }

    private fun initializeSessionAndPlayer() {

        val videoItem = MediaItem.Builder()
            .setUri("https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Bersama Dicoding, Kembangkan Dirimu Menjadi Talenta Digital Berstandar Global")
                    .setArtist("Dicoding")
                    .build()
            ).build()
        val audioItem = MediaItem.Builder()
            .setUri("https://github.com/dicodingacademy/assets/raw/main/android_intermediate_academy/bensound_ukulele.mp3")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Ukulele")
                    .setArtist("Bensound")
                    .build()
            ).build()

        val player = ExoPlayer.Builder(this).build().also { exoPlayer ->
            exoPlayer.setMediaItem(videoItem)
            exoPlayer.addMediaItem(audioItem)
            exoPlayer.prepare()
        }

        mediaSession = MediaSession.Builder(this, player).build()

    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

}