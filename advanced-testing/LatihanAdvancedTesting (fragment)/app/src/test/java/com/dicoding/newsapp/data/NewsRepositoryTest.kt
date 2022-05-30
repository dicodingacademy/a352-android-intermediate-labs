package com.dicoding.newsapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.newsapp.MainDispatcherRule
import com.dicoding.newsapp.data.local.room.NewsDao
import com.dicoding.newsapp.data.remote.retrofit.ApiService
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsRepositoryTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiService: ApiService
    private lateinit var newsDao : NewsDao
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        newsDao = FakeNewsDao()
        newsRepository = NewsRepository(apiService, newsDao)
    }

    @Test
    fun `when getNews Should Not Null`() = runTest {
        val expectedNews = DataDummy.generateDummyNewsResponse()
        val actualNews = apiService.getNews("apiKey")
        Assert.assertNotNull(actualNews)
        Assert.assertEquals(expectedNews.articles.size, actualNews.articles.size)
    }

    @Test
    fun `when saveNews Should Exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.saveNews(sampleNews)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertTrue(actualNews.contains(sampleNews))
        Assert.assertTrue(newsDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }

    @Test
    fun `when deleteNews Should Not Exist in getBookmarkedNews`() = runTest {
        val sampleNews = DataDummy.generateDummyNewsEntity()[0]
        newsDao.saveNews(sampleNews)
        newsDao.deleteNews(sampleNews.title)
        val actualNews = newsDao.getBookmarkedNews().getOrAwaitValue()
        Assert.assertFalse(actualNews.contains(sampleNews))
        Assert.assertFalse(newsDao.isNewsBookmarked(sampleNews.title).getOrAwaitValue())
    }
}

