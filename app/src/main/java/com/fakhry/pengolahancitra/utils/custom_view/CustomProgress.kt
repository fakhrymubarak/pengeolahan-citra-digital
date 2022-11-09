package com.fakhry.pengolahancitra.utils.custom_view

import android.content.Context
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import com.fakhry.pengolahancitra.R
import com.fakhry.pengolahancitra.utils.toDp

class CustomProgress(context: Context) {
    private val alertDialog: AlertDialog
    private val builder: AlertDialog.Builder

    init {
        val relative = RelativeLayout(context)
        relative.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        val progress = ProgressBar(context)
        val progressLayout = RelativeLayout.LayoutParams(
            40.toDp(context).toInt(),
            40.toDp(context).toInt()
        )
        progressLayout.addRule(RelativeLayout.CENTER_IN_PARENT)
        progress.layoutParams = progressLayout
        relative.addView(progress)
        builder = AlertDialog.Builder(context)
        builder.setView(relative)
        alertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawableResource(R.color.color_transparent)
        alertDialog.setCancelable(false)
    }

    private fun startLoading() {
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    private fun stopLoading() {
        if (alertDialog.isShowing) {
            alertDialog.dismiss()
        }
    }

    fun showLoading(state: Boolean) {
        if (state) {
            startLoading()
        } else {
            stopLoading()
        }
    }
}