package com.tripbook.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding, V : BaseViewModel>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    private var _binding: B? = null
    val binding get() = _binding!!

    abstract val viewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
        init()
    }

    protected abstract fun init()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun hideKeyboard() {
        requireActivity().currentFocus?.let {
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    val layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val r = Rect()
        binding.root.getWindowVisibleDisplayFrame(r)
        val screenHeight = binding.root.rootView.height

        val keypadHeight = screenHeight - r.bottom
        if (keypadHeight > screenHeight * 0.15) { // 키보드가 올라가 있을 때
            if (viewModel.isKeyboardUp.value.not()) {
                viewModel.setKeyboard(true)
                binding.root.setOnClickListener {
                    hideKeyboard()
                }
            }
        } else { // 키보드가 내려가있을 때
            if (viewModel.isKeyboardUp.value) viewModel.setKeyboard(false)
        }
    }

    override fun onStop() {
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
        super.onStop()
    }
}