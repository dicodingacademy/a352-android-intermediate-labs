package com.dicoding.newsapp.utils

import com.dicoding.newsapp.data.local.entity.NewsEntity

object DataDummy {

    fun generateDummyNewsEntity(): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        for (i in 0..10) {
            val news = NewsEntity(
                "Title $i",
                "2022-02-22T22:22:22Z",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "https://www.dicoding.com/",
            )
            newsList.add(news)
        }
        return newsList
    }
}