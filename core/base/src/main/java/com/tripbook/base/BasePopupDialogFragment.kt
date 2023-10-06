package com.tripbook.base

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

// 일반적인 팝업 용도로 사용되는 dialogFragment
abstract class BasePopupDialogFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : DialogFragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    //    ○ title, positive 필수
    //    ○ 텍스트, negative 버튼 nullable

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        setText()
        setClickListener()
    }

    abstract fun setText()
    abstract fun setClickListener()

    private fun Context.dialogFragmentResize() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val window = this@BasePopupDialogFragment.dialog?.window

        if (Build.VERSION.SDK_INT < 30) {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val x = (size.x * 0.7f).toInt()
            window?.attributes?.width = x
        } else {
            val rect = windowManager.currentWindowMetrics.bounds
            val x = (rect.width() * 0.7f).toInt()
            window?.attributes?.width = x
        }
        window?.attributes = window?.attributes as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
