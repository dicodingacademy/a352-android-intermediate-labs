package com.dicoding.picodiploma.productdetail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.productdetail.databinding.ActivityMainBinding
import com.dicoding.picodiploma.productdetail.helper.withCurrencyFormat
import com.dicoding.picodiploma.productdetail.helper.withDateFormat
import com.dicoding.picodiploma.productdetail.helper.withNumberingFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupData()
    }

    private fun setupAction() {
        binding.settingImageView.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupData() {
        val repository = RemoteDataSource(this)
        val product = repository.getDetailProduct().apply {
            binding.apply {
                previewImageView.setImageResource(image)
                nameTextView.text = name
                storeTextView.text = store
                colorTextView.text = color
                sizeTextView.text = size
                descTextView.text = desc
                priceTextView.text = price.withCurrencyFormat()
                dateTextView.text = getString(R.string.dateFormat, date.withDateFormat())
                ratingTextView.text = getString(R.string.ratingFormat, rating.withNumberingFormat(), countRating.withNumberingFormat())
            }
        }
    }
}