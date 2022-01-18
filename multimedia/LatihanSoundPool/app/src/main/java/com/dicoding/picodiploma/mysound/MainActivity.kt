package com.dicoding.picodiploma.mysound

import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSound = findViewById<Button>(R.id.btn_soundpool)

        sp = SoundPool.Builder()
                .setMaxStreams(10)
                .build()

        /*
        Tambahkan listener ke soundpool jika proses load sudah selesai
         */
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@MainActivity, "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }

        /*
        Load raw clinking_glasses ke soundpool, jika selesai maka id nya dimasukkan ke variable soundId
         */
        soundId = sp.load(this, R.raw.clinking_glasses, 1) // in 2nd param u have to pass your desire ringtone

        btnSound.setOnClickListener {
            if (spLoaded) {
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
        }
    }

}
