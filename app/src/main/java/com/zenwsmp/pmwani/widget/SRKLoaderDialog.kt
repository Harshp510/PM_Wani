/*
 * Copyright (c) 2018. Kaushal Patel
 * Developed by Kaushal Patel for NicheTech Computer Solutions Pvt. Ltd. use only.
 *
 * @author Kaushal Patel
 */

package `in`.ticketninja.widget

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.zenwsmp.pmwani.R


class SRKLoaderDialog : AppCompatDialog {

    constructor(context: Context?) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    private constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.layout_dialog_progressbar)

            setCancelable(false)

            val ivLoader = findViewById<ImageView>(R.id.ivLoader)
            if (ivLoader != null) {
                Glide.with(context)
                        .load(R.drawable.loader1)
                        .placeholder(R.drawable.loader1)
                        .into(ivLoader)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
