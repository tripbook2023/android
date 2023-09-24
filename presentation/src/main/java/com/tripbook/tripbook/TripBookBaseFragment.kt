package com.tripbook.tripbook

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tripbook.base.BaseFragment

abstract class TripBookBaseFragment<B : ViewDataBinding, V : TripBookBaseViewModel>(
    @LayoutRes val layoutRes: Int
) : BaseFragment<B, V>(
    layoutRes
) {
    abstract override val viewModel: V

    override fun init() {
        collectData()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorFlow.collect {
                    findNavController().navigate(
                        NavGraphDirections.actionGlobalErrorFragment(
                            it?.localizedMessage ?: ""
                        )
                    )
                }
            }
        }
    }
}