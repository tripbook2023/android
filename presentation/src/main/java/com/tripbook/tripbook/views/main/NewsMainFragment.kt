package com.tripbook.tripbook.views.main

import androidx.fragment.app.viewModels
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentMainBinding
import com.tripbook.tripbook.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsMainFragment : BaseFragment<FragmentMainBinding, NewsViewModel>(R.layout.fragment_main) {
    override val viewModel: NewsViewModel by viewModels()

    override fun init() {
        binding.viewmodel = viewModel
    }
}