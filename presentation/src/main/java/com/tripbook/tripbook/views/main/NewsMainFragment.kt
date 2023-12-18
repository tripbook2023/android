package com.tripbook.tripbook.views.main

import android.view.Gravity
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripbook.base.BaseFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentMainBinding
import com.tripbook.tripbook.domain.model.SortType
import com.tripbook.tripbook.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsMainFragment : BaseFragment<FragmentMainBinding, NewsViewModel>(R.layout.fragment_main) {
    override val viewModel: NewsViewModel by viewModels()

    private val articleAdapter by lazy {
        ArticleDetailAdapter {
            findNavController().navigate(
                NewsMainFragmentDirections.actionNewsMainFragmentToTripDetailFragment(
                    articleId = it
                )
            )
        }
    }

    override fun init() {
        binding.viewmodel = viewModel
        initRecyclerView()
        initListener()
        collectProperties()
    }

    private fun initListener() {
        binding.imageviewSearch.setOnClickListener {
            findNavController().navigate(NewsMainFragmentDirections.actionNewsMainFragmentToSearchFragment())
        }
        binding.imageviewSort.setOnClickListener {
            val menu = PopupMenu(requireContext(), it, Gravity.END)
            menu.menuInflater.inflate(R.menu.menu_sort, menu.menu)
            menu.setOnMenuItemClickListener { item ->
                viewModel.setSortType(SortType.from(item.title.toString()))
                true
            }
            menu.show()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerMain.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(SpaceDecoration(verticalHeight = 16))
            adapter = articleAdapter
        }
    }

    private fun collectProperties() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articleList.collect {
                    articleAdapter.submitData(it)
                }
            }
        }
    }

}