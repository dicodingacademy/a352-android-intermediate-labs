package com.dicoding.myunlimitedquotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.myunlimitedquotes.network.QuoteResponseItem

@Database(
    entities = [QuoteResponseItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: QuoteDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): QuoteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    QuoteDatabase::class.java, "quote_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}