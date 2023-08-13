package com.tripbook.tripbook.views.news

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentNewsMainBinding
import com.tripbook.tripbook.viewmodel.NewsMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsMainFragment: BaseFragment<FragmentNewsMainBinding, NewsMainViewModel>(
    R.layout.fragment_news_main
) {
    override val viewModel: NewsMainViewModel by viewModels()
    private val adapter by lazy {
        CardAdapter().also {
            binding.recyclerNews.adapter = it
        }
    }
    override fun init() {
        collectData()
    }

    private fun collectData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainItem.collectLatest { mainItem ->
                    adapter.submitList(mainItem)
                }
            }
        }
    }
}