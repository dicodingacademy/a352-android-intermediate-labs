package com.dicoding.picodiploma.myserviceapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class MyBoundService : Service() {

    companion object {
        private val TAG = MyBoundService::class.java.simpleName
    }
    private var binder = MyBinder()

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    val numberLiveData: MutableLiveData<Int> = MutableLiveData()

    /*
    Method yang akan dipanggil ketika service diikatkan ke activity
     */
    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ")
        serviceScope.launch {
            for (i in 1..50) {
                delay(1000)
                Log.d(TAG, "Do Something $i")
                numberLiveData.postValue(i)
            }
            Log.d(TAG, "Service dihentikan")
        }
        return binder
    }

    /*
    Ketika semua ikatan sudah dilepas maka ondestroy akan secara otomatis dipanggil
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    /*
    Method yang akan dipanggil ketika service dilepas dari activity
     */
    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind: ")
        serviceJob.cancel()
        return super.onUnbind(intent)
    }

    /*
    Method yang akan dipanggil ketika service diikatkan kembali
     */
    override fun onRebind(intent: Intent) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    internal inner class MyBinder : Binder() {
        val getService: MyBoundService = this@MyBoundService
    }
}
