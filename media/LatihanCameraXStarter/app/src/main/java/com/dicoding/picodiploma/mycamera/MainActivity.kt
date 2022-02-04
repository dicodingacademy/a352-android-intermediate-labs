package com.dicoding.picodiploma.mycamera

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.mycamera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraXButton.setOnClickListener {
            Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
        binding.cameraButton.setOnClickListener {
            Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
        binding.galleryButton.setOnClickListener {
            Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
        binding.uploadButton.setOnClickListener {
            Toast.makeText(this, "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

}