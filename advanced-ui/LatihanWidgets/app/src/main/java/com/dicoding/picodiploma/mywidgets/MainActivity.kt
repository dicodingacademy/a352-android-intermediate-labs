package com.dicoding.picodiploma.mywidgets

import android.app.job.JobScheduler
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){

    private lateinit var workManager: WorkManager

    companion object {
        private const val UPDATE_TAG = "update widget"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workManager = WorkManager.getInstance(this)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val btnStop = findViewById<Button>(R.id.btn_stop)

        btnStart.setOnClickListener { startWork() }
        btnStop.setOnClickListener { cancelWork() }
    }

    private fun startWork() {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<UpdateWidgetWorker>(15, TimeUnit.MINUTES)
            .addTag(UPDATE_TAG)
            .build()
        workManager.enqueue(periodicWorkRequest)

        Toast.makeText(this, "WorkManager started", Toast.LENGTH_SHORT).show()
    }

    private fun cancelWork() {
        workManager.cancelAllWorkByTag(UPDATE_TAG)
        Toast.makeText(this, "WorkManager canceled", Toast.LENGTH_SHORT).show()
    }
}


