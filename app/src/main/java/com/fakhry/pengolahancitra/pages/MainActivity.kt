package com.fakhry.pengolahancitra.pages

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.fakhry.pengolahancitra.Utils
import com.fakhry.pengolahancitra.databinding.ActivityMainBinding
import com.fakhry.pengolahancitra.utils.collectLifecycleFlow
import com.fakhry.pengolahancitra.utils.custom_view.CustomProgress
import com.fakhry.pengolahancitra.utils.isVisible
import com.fakhry.pengolahancitra.utils.viewBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

/**
 * Created by Fakhry on 28/05/2021.
 */
class MainActivity : AppCompatActivity(), PermissionListener {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var customProgress: CustomProgress
    private lateinit var defaultBitmap: Bitmap
    private var isPictureAdded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()
        initObserver()
    }

    private fun initView() {
        customProgress = CustomProgress(this)
    }

    private fun initListener() {
        binding.apply {
            btnTakePicture.setOnClickListener {
                openImagePicker()
            }
            btnSave.setOnClickListener {
                if (isPictureAdded) {
                    Utils.saveImage(defaultBitmap, this@MainActivity, "PCD")
                } else showToast("Gambar belum ditambahkan.")
            }

            btnImageInformation.setOnClickListener { viewModel.updateBitmapWithAutomaticThresholding() }
            btnSetToGrayscale.setOnClickListener { viewModel.updateBitmapToGrayscale() }
            btnMonochrome.setOnClickListener { viewModel.updateBitmapToMonochrome() }

            btnFlipHorizontal.setOnClickListener { viewModel.updateBitmapWithHorizontalFlip() }
            btnFlipVertical.setOnClickListener { viewModel.updateBitmapWithVerticalFlip() }

            btnRotateLeft90.setOnClickListener { viewModel.updateBitmapWithRotateLeft90() }
            btnRotateRight90.setOnClickListener { viewModel.updateBitmapWithRotateRight90() }

            btnNoiseSalt.setOnClickListener { viewModel.updateBitmapWithSaltPaper() }
            btnAvgFilter.setOnClickListener { viewModel.updateBitmapWithAvgFilter() }

            btnRgbToHsv.setOnClickListener { viewModel.updateBitmapToHsv() }
            btnRgbToHsvLibrary.setOnClickListener { viewModel.updateBitmapToHsv(true) }

            btnUndo.setOnClickListener { viewModel.undoChanges() }
            btnRedo.setOnClickListener { viewModel.redoChanges() }
        }
    }

    private fun initObserver() {
        collectLifecycleFlow(viewModel.loadingState) { state -> customProgress.showLoading(state) }

        collectLifecycleFlow(viewModel.activeBitmapState) { activeBitmap ->
            isPictureAdded = activeBitmap != null
            if (activeBitmap != null) {
                defaultBitmap = activeBitmap
            }
            binding.ivImageTaken.setImageBitmap(activeBitmap)
        }

        collectLifecycleFlow(viewModel.isButtonUndoEnabled) { binding.btnUndo.isVisible(it) }

        collectLifecycleFlow(viewModel.isButtonRedoEnabled) { binding.btnRedo.isVisible(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                val fileUri = data?.data!!
                val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(fileUri))
                viewModel.setBitmap(bitmap)
            }
            ImagePicker.RESULT_ERROR -> {
                showToast(ImagePicker.getError(data))
                isPictureAdded = false
            }
            else -> {
                showToast("Gagal mengupload foto")
                isPictureAdded = false
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this).start()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        openImagePicker()
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
        Toast.makeText(
            this,
            "Tidak bisa mengakses kamera dan storage, silakan mengubahnya di setting.",
            Toast.LENGTH_LONG
        ).show()
    }
}