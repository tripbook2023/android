package com.tripbook.tripbook.views.tripAdd

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripbook.base.BaseBottomSheetFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.adapter.LocationListAdapter
import com.tripbook.tripbook.databinding.FragmentNewsAddLocationBottomBinding
import com.tripbook.tripbook.viewmodel.NewsAddViewModel

class NewsAddLocationBottomFragment :
    BaseBottomSheetFragment<FragmentNewsAddLocationBottomBinding, NewsAddViewModel>(R.layout.fragment_news_add_location_bottom) {

    override val viewModel: NewsAddViewModel by activityViewModels()

    override fun init() {
        binding.viewModel = viewModel
        val adapter = LocationListAdapter { selectedPos ->
            viewModel.setListIndex(selectedPos)
        }
        binding.locationList.adapter = adapter
        binding.locationList.layoutManager = LinearLayoutManager(requireContext())

        binding.locationButton.setOnClickListener {
            viewModel.setLocation(viewModel.locationList.value[viewModel.locationListIndex.value].place_name)
            dismiss()
        }

        binding.locationSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchKeyword(text.toString())
        }
    }
}