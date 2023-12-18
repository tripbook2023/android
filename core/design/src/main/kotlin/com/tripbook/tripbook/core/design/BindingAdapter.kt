package com.tripbook.tripbook.core.design

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@Suppress("UNUSED")
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

    // 회원가입 이미지 설정 시에만 사용 => placeholder 때문
    @JvmStatic
    @BindingAdapter("signUpProfileImgUri")
    fun setSignUpProfileImage(imageView: ImageView, uri: Uri?) {
        Glide.with(imageView.context)
            .load(uri)
            .placeholder(R.drawable.icn_pic_36)
            .circleCrop()
            .into(imageView)
    }

    // 프로필 사진 띄울 때 사용 => 내 정보, 여행소식 상세보기 등
    // 프로필 사진을 지정 안했을 경우(null)은 기본 이미지 뜨도록 설정해둠
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
                .load(R.drawable.tripbook_image)
                .circleCrop()
                .into(imageView)
        }

    }

    // 일반 이미지 띄울 때 사용 => 프로필 사진처럼 circleCrop이 필요하지 않은 경우 사용
    // ex) thumbnail
    @JvmStatic
    @BindingAdapter("imgUrl")
    fun setImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgURL")
    fun setImageWithString(imageView: ImageView, urlString: String?) {
        urlString?.let {
            Glide.with(imageView.context)
                .load(it)
                .placeholder(R.drawable.icn_pic_36)
                .into(imageView)
        } ?: imageView.setImageResource(R.drawable.icn_pic_36)

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
    @BindingAdapter("app:tint")
    fun setImageViewColor(imageView: ImageView, @ColorInt color: Int) {
        imageView.setColorFilter(color)
    }

    @JvmStatic
    @BindingAdapter("profileImg")
    fun setProfileImage(imageView: ImageView, urlString: String?) {
        urlString?.let {
            Glide.with(imageView.context)
                .load(urlString)
                .circleCrop()
                .into(imageView)
        } ?: kotlin.run {
            Glide.with(imageView.context)
                .load(R.drawable.icn_pic_36)
                .circleCrop()
                .into(imageView)
        }

    }
}
