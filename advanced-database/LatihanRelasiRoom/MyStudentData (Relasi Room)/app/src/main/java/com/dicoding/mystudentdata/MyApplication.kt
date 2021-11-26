package com.dicoding.mystudentdata

import android.app.Application
import com.dicoding.mystudentdata.database.StudentDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    val database by lazy { StudentDatabase.getDatabase(this) }
}