package com.tripbook.tripbook.core.design

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imgUri", "defaultImg")
    fun setImageUri(imageView: ImageView, uri: Uri?, default: Boolean) {
        if (default) {
            imageView.setImageURI((uri))
        } else {
            Glide.with(imageView.context)
                .load(uri)
                .circleCrop()
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("marginBottom")
    fun setButtonMarginBottom(view: View, dimen: Float) {
        (view.layoutParams as ViewGroup.MarginLayoutParams).let {
            it.bottomMargin = dimen.toInt()
            view.layoutParams = it
        }
    }

    @JvmStatic
    @BindingAdapter("validDrawable")
    fun setValidAgeIcon(view: EditText, icon: Int) {
        view.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
    }
}
