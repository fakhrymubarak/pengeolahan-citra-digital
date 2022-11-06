package com.example.pengolahancitra

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pengolahancitra.databinding.ActivityMainBinding
import com.example.pengolahancitra.helpers.image_processing.ImageFilters
import com.example.pengolahancitra.helpers.image_processing.ImageFlipping
import com.example.pengolahancitra.helpers.image_processing.ImageRotating
import com.example.pengolahancitra.helpers.image_restoration.NoiseRemover
import com.example.pengolahancitra.helpers.image_restoration.NoiseSetter
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.runBlocking

/**
 * Created by Fakhry on 28/05/2021.
 */
class MainActivity : AppCompatActivity(), PermissionListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var defaultBitmap: Bitmap
    private var isPictureAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnTakePicture.setOnClickListener {
                addBitmap()
            }
            btnSave.setOnClickListener {

                if (isPictureAdded) {
                    Utils.saveImage(defaultBitmap, this@MainActivity, "PCD")
                } else showToast("Gambar belum ditambahkan.")
            }
            btnImageInformation.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = ImageFilters.automaticThresholding(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnSetToGrayscale.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = ImageFilters.setGreyFilter(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnFlipHorizontal.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = ImageFlipping.horizontalFlip(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnFlipVertical.setOnClickListener {
                if (isPictureAdded) {
                    runBlocking {
                        defaultBitmap = ImageFlipping.verticalFlip(defaultBitmap)
                    }
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnRotateLeft90.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = ImageRotating.rotateLeft90(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnRotateRight90.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = ImageRotating.rotateRight90(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnMonochrome.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = ImageFilters.setBlackAndWhite(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnNoiseSalt.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = NoiseSetter.setNoiseSaltAndPepper(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }
            btnAvgFilter.setOnClickListener {
                if (isPictureAdded) {
                    defaultBitmap = NoiseRemover.averageFilter(defaultBitmap)
                    binding.ivImageTaken.setImageBitmap(defaultBitmap)
                } else showToast("Gambar belum ditambahkan.")
            }


            btnTakePicture.setOnLongClickListener {
                showToast("Ambil Citra")
                true
            }
            btnSave.setOnLongClickListener {
                showToast("Simpan Citra")
                true
            }
            btnImageInformation.setOnLongClickListener {
                showToast("Tampilkan Detail Citra")
                true
            }
            btnSetToGrayscale.setOnLongClickListener {
                showToast("Ubah Citra Ke Grayscale")
                true
            }
            btnFlipHorizontal.setOnLongClickListener {
                showToast("Flip Vertikal")
                true
            }
            btnFlipVertical.setOnLongClickListener {
                showToast("Flip Horizontal")
                true
            }
            btnRotateLeft90.setOnLongClickListener {
                showToast("Rotasi ke kiri")
                true
            }
            btnRotateRight90.setOnLongClickListener {
                showToast("Rotasi ke kanan")
                true
            }
            btnMonochrome.setOnLongClickListener {
                showToast("Ubah Citra ke Black and White")
                true
            }
            btnNoiseSalt.setOnLongClickListener {
                showToast("Berikan noise salt and papper ke citra")
                true
            }
            btnAvgFilter.setOnLongClickListener {
                showToast("Restorasi citra")
                true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                val fileUri = data?.data!!
                //Set Bitmap
                defaultBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(fileUri))

                isPictureAdded = true
                binding.ivImageTaken.setImageBitmap(defaultBitmap)
                binding.ivImageTaken.elevation = 0F
            }
            ImagePicker.RESULT_ERROR -> {
                showToast("ImagePicker.getError(data)")
                isPictureAdded = false
            }
            else -> {
                showToast("Gagal mengupload foto")
                isPictureAdded = false
            }
        }
    }

    private fun addBitmap() {
        ImagePicker.with(this).start()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        addBitmap()
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(
            this,
            "Tidak bisa mengakses kamera dan storage, silakan mengubahnya di setting.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        p0: PermissionRequest?,
        p1: PermissionToken?
    ) {
    }
}