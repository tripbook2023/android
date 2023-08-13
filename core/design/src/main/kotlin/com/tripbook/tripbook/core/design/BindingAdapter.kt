package com.tripbook.tripbook.core.design

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("borderColor")
    fun setBirthBorderColor(textView: TextView, valid: Boolean) {
        if (valid) {
            textView.setBackgroundResource(R.drawable.border_text_birth_after)
        } else {
            textView.setBackgroundResource(R.drawable.border_text_birth_before)
        }
    }

    @JvmStatic
    @BindingAdapter("imgUri")
    fun setImageUri(imageView: ImageView, uri: Uri?) {
        Glide.with(imageView.context)
            .load(uri)
            .placeholder(R.drawable.icn_pic_36)
            .circleCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgUri")
    fun setImageUriWithString(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("backgroundImg")
    fun setImageBackgroundWithView(view: View, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    view.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
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
