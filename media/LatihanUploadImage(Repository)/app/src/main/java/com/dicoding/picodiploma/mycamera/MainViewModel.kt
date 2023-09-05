package com.dicoding.picodiploma.mycamera

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.mycamera.data.UploadRepository
import java.io.File

class MainViewModel(private val repository: UploadRepository) : ViewModel() {
    fun uploadImage(file: File, description: String) = repository.uploadImage(file, description)
}