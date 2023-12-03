package com.tripbook.tripbook.views.trip.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentSearchBinding
import com.tripbook.tripbook.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding, SearchViewModel>(R.layout.fragment_search) {
    override val viewModel: SearchViewModel by viewModels()

    private val searchKeywordAdapter by lazy {
        SearchKeywordAdapter(
            onClick = { viewModel.clickRecentKeyword(it) },
            onDelete = { viewModel.deleteSearchKeyword(it) }
        )
    }

    private val searchResultAdapter by lazy {
        SearchResultAdapter {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToTripDetailFragment(
                    articleId = it
                )
            )
        }
    }

    override fun init() {
        binding.vm = viewModel
        initSearchAdapter()
        initResultAdapter()
        initListener()
        collectProperties()
    }

    private fun collectProperties() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchedItem.collect {
                    searchResultAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.keywordList.collect {
                    searchKeywordAdapter.submitList(it)
                }
            }
        }
    }

    private fun initListener() = binding.run {
        imageviewBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initSearchAdapter() = binding.run {
        with(recyclerRecent) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchKeywordAdapter
        }
    }

    private fun initResultAdapter() = binding.run {
        with(recyclerResult) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchResultAdapter
        }
    }
}