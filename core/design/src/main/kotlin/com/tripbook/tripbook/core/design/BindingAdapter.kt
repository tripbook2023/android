package com.tripbook.tripbook.core.design

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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
    @BindingAdapter("signUpProfileImgUri")
    fun setSignUpProfileImage(imageView: ImageView, uri: Uri?) {
        Glide.with(imageView.context)
            .load(uri)
            .placeholder(R.drawable.icn_pic_36)
            .circleCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("profileImgUri")
    fun setProfileImage(imageView: ImageView, uri: Uri?) {
        uri?.let {
            Glide.with(imageView.context)
                .load(uri)
                .circleCrop()
                .into(imageView)
        } ?: kotlin.run {
            Glide.with(imageView.context)
                .load(R.drawable.icn_pic_36)
                .circleCrop()
                .into(imageView)
        }

    }

        // 일반 이미지 띄울 때 사용 => 프로필 사진처럼 circleCrop이 필요하지 않은 경우 사용
        // ex) thumbnail
        @JvmStatic
        @BindingAdapter("imgUri")
        fun setImage(imageView: ImageView, uri: Uri?) {
            Glide.with(imageView.context)
                .load(uri)
                .into(imageView)
        }

    @JvmStatic
    @BindingAdapter("marginBottom")
    fun setButtonMarginBottom(view: View, dimen: Float) {
        (view.layoutParams as ViewGroup.MarginLayoutParams).let {
            it.bottomMargin = dimen.toInt()
            view.layoutParams = it
        }
    }
}
