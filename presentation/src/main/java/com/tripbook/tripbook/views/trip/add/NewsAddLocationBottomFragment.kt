package com.tripbook.tripbook.views.trip.add

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
            viewModel.setLocationListIndex(selectedPos)
        }
        binding.locationList.adapter = adapter
        binding.locationList.layoutManager = LinearLayoutManager(requireContext())

        binding.locationButton.setOnClickListener {
            viewModel.setLocation(viewModel.locationSearchList.value[viewModel.locationListIndex.value])
            dismiss()
        }

        binding.locationSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchLocation(text.toString())
            adapter.keyword = text.toString()
        }
    }

    override fun onDestroyView() {
        viewModel.initLocationList()
        super.onDestroyView()
    }
}