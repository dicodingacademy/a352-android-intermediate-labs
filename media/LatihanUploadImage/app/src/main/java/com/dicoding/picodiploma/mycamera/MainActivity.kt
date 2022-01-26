package com.dicoding.picodiploma.mycamera

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.mycamera.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var getFile: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.cameraXButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
        binding.cameraButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            launcherIntentCamera.launch(intent)
        }
        binding.galleryButton.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, "Choose a Picture")
            launcherIntentGallery.launch(chooser)

        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val getBitmap = BitmapFactory.decodeFile(getFile?.path)

            val matrix = Matrix()
            val result: Bitmap = if (isBackCamera) {
                matrix.postRotate(90f)
                Bitmap.createBitmap(
                    getBitmap,
                    0,
                    0,
                    getBitmap.width,
                    getBitmap.height,
                    matrix,
                    true
                )
            } else {
                matrix.postRotate(-90f)
                matrix.postScale(-1f, 1f, getBitmap.width / 2f, getBitmap.height / 2f)
                Bitmap.createBitmap(
                    getBitmap,
                    0,
                    0,
                    getBitmap.width,
                    getBitmap.height,
                    matrix,
                    true
                )
            }

            binding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val imageBitmap = it.data?.extras?.get("data") as Bitmap
            getFile = bitmapToFile(imageBitmap)

            val getBitmap = BitmapFactory.decodeFile(getFile?.path)
            binding.previewImageView.setImageBitmap(getBitmap)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val imageBitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImg)
            } else {
                val source = ImageDecoder.createSource(this.contentResolver, selectedImg)
                ImageDecoder.decodeBitmap(source)
            }

            getFile = bitmapToFile(imageBitmap)

            val getBitmap = BitmapFactory.decodeFile(getFile?.path)
            binding.previewImageView.setImageBitmap(getBitmap)
        }
    }

    private fun bitmapToFile(imageBitmap: Bitmap): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else filesDir

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                CameraActivity.FILENAME_FORMAT,
                Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        ).apply { createNewFile() }

        val bos = ByteArrayOutputStream()
        imageBitmap.compress(CompressFormat.PNG, 0, bos)

        FileOutputStream(photoFile).apply {
            write(bos.toByteArray())
            flush()
            close()
        }
        return photoFile
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}