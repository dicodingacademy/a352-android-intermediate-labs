package com.dicoding.picodiploma.mycamera.data.di

import com.dicoding.picodiploma.mycamera.data.UploadRepository
import com.dicoding.picodiploma.mycamera.data.api.ApiConfig

object Injection {
    fun provideRepository(): UploadRepository {
        val apiService = ApiConfig.getApiService()
        return UploadRepository.getInstance(apiService)
    }
}