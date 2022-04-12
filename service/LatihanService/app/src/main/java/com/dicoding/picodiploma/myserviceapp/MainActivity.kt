package com.dicoding.picodiploma.myserviceapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.myserviceapp.MyBoundService.MyBinder

class MainActivity : AppCompatActivity() {

    private lateinit var tvBoundService: TextView
    private var boundStatus = false
    private lateinit var boundService: MyBoundService

    /*
    Service Connection adalah interface yang digunakan untuk menghubungkan antara boundservice dengan activity
     */
    private val connection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            boundStatus = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBoundService.MyBinder
            boundService = myBinder.getService
            boundStatus = true

            getNumberFromService()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceIntent = Intent(this, MyService::class.java)

        val btnStartService = findViewById<Button>(R.id.btn_start_service)
        btnStartService.setOnClickListener { 
            startService(serviceIntent)
        }

        val btnStopService = findViewById<Button>(R.id.btn_stop_service)
        btnStopService.setOnClickListener {
            stopService(serviceIntent)
        }

        val btnStartForegroundService = findViewById<Button>(R.id.btn_start_foreground_service)

        val foregroundServiceIntent = Intent(this,  MyForegroundService::class.java)
        btnStartForegroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, foregroundServiceIntent)
        }

        val btnStopForegroundService = findViewById<Button>(R.id.btn_stop_foreground_service)
        btnStopForegroundService.setOnClickListener {
            stopService(serviceIntent)
        }

        val boundServiceIntent = Intent(this, MyBoundService::class.java)

        val btnStartBoundService = findViewById<Button>(R.id.btn_start_bound_service)
        tvBoundService = findViewById(R.id.tv_bound_service_number)

        btnStartBoundService.setOnClickListener { 
            bindService(boundServiceIntent, connection, BIND_AUTO_CREATE)
        }

        val btnStopBoundService = findViewById<Button>(R.id.btn_stop_bound_service)
        btnStopBoundService.setOnClickListener {
            unbindService(connection)
        }

    }

    private fun getNumberFromService() {
        boundService.numberLiveData.observe(this){
            tvBoundService.text = it.toString()
        }
    }

    override fun onStop() {
        super.onStop()
        if (boundStatus) {
            unbindService(connection)
            boundStatus = false
        }
    }
}