package com.fakhry.pengolahancitra.pages

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _activeBitmapState = MutableStateFlow<Bitmap?>(null)
    val activeBitmapState = _activeBitmapState.asStateFlow()

    private val _listChangesToUndo = arrayListOf<Bitmap>()
    private val _listChangesToRedo = arrayListOf<Bitmap>()

    val isButtonUndoEnabled = MutableStateFlow(false)
    val isButtonRedoEnabled = MutableStateFlow(false)


    private fun addUndo(bitmap: Bitmap) {
        _listChangesToUndo.add(bitmap)
        isButtonUndoEnabled.value = true
        Log.e("MainViewModel", "undoChanges -> $_listChangesToUndo")
    }

    fun undoChanges() {
        val lastUndoBitmap = _listChangesToUndo.lastOrNull()
        val currentActiveBitmap = _activeBitmapState.value
        if (lastUndoBitmap != null) {
            _activeBitmapState.value = lastUndoBitmap

            _listChangesToUndo.removeLast()
        } else {
            _activeBitmapState.value = null
            isButtonUndoEnabled.value = false
        }

        if (currentActiveBitmap != null) addRedo(currentActiveBitmap)
    }

    private fun addRedo(bitmap: Bitmap) {
        _listChangesToRedo.add(bitmap)
        isButtonRedoEnabled.value = true
        Log.e("MainViewModel", "redoChanges -> $_listChangesToRedo")
    }

    fun redoChanges() {
        val lastRedoBitmap = _listChangesToRedo.lastOrNull()
        val currentActiveBitmap = _activeBitmapState.value
        if (lastRedoBitmap != null) {
            _activeBitmapState.value = lastRedoBitmap

            _listChangesToRedo.removeLast()
            isButtonRedoEnabled.value = _listChangesToRedo.isNotEmpty()
        }

        if (currentActiveBitmap != null) addUndo(currentActiveBitmap)
    }

    private fun clearRedoChanges() {
        _listChangesToRedo.clear()
        isButtonRedoEnabled.value = false
    }

    fun setBitmap(bitmap: Bitmap) {
        isButtonUndoEnabled.value = true
        _activeBitmapState.value?.let { addUndo(it) }
        _activeBitmapState.value = bitmap
        clearRedoChanges()
    }
}