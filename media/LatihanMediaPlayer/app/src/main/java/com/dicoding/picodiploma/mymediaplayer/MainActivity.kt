package com.dicoding.picodiploma.mymediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var mMediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlay = findViewById<Button>(R.id.btn_play)
        val btnStop = findViewById<Button>(R.id.btn_stop)
        btnPlay.setOnClickListener {
            /*
           Untuk mengirim perintah play
           */
            if (!isReady) {
                mMediaPlayer?.prepareAsync()
            } else {
                if (mMediaPlayer?.isPlaying as Boolean) {
                    mMediaPlayer?.pause()
                } else {
                    mMediaPlayer?.start()
                }
            }
        }
        btnStop.setOnClickListener {
            /*
            Untuk mengirim perintah stop
            */
            if (mMediaPlayer?.isPlaying as Boolean || isReady) {
                mMediaPlayer?.stop()
                isReady = false
            }
        }

        init()
    }

    /*
   Method ini berfungsi untuk menginisialisasi mediaplayer
   */
    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer?.setAudioAttributes(attribute)

        val afd = applicationContext.resources.openRawResourceFd(R.raw.guitar_background)
        try {
            mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        /**
         * Called when MediaPlayer is ready
         */
        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
        }

        /**
         * Called when MediaPlayer is error
         */
        mMediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }

}