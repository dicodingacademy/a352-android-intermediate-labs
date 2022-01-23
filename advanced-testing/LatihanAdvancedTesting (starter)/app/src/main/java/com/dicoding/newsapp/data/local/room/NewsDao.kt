package com.dicoding.newsapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.newsapp.data.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE title = :newsTitle")
    suspend fun deleteNews(newsTitle: String)

    @Update
    suspend fun updateNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title)")
    fun isNewsBookmarked(title: String): LiveData<Boolean>
}