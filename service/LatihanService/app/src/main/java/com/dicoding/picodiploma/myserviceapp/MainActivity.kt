package com.dicoding.picodiploma.myserviceapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.myserviceapp.MyBoundService.MyBinder

class MainActivity : AppCompatActivity() {

    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService

    /*
    Service Connection adalah interface yang digunakan untuk menghubungkan antara boundservice dengan activity
     */
    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
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

        val btnStartBoundService = findViewById<Button>(R.id.btn_start_bound_service)
        btnStartBoundService.setOnClickListener { 
            val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
            bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
        }

        val btnStopBoundService = findViewById<Button>(R.id.btn_stop_bound_service)
        btnStopBoundService.setOnClickListener {
            unbindService(mServiceConnection)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        /*
        Pemanggilan unbind di dalam ondestroy ditujukan untuk mencegah memory leaks dari bound services
         */
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}