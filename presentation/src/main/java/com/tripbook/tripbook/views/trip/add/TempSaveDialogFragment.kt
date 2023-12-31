package com.tripbook.tripbook.views.trip.add

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripbook.base.BaseDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.adapter.TempSaveAdapter
import com.tripbook.tripbook.databinding.FragmentTempSaveBinding
import com.tripbook.tripbook.viewmodel.NewsAddViewModel
import kotlinx.coroutines.launch


class TempSaveDialogFragment : BaseDialogFragment<FragmentTempSaveBinding, NewsAddViewModel>(R.layout.fragment_temp_save) {

    override val viewModel: NewsAddViewModel by activityViewModels()
    private lateinit var adapter: TempSaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    private fun deleteItem(position: Int) {
        binding.tempSaveList.itemAnimator = null
        val tempList = ArrayList(adapter.currentList)
        tempList.removeAt(position)
        adapter.submitList(tempList) {
            adapter.notifyItemRangeChanged(position, adapter.itemCount - position)
            binding.tempSaveList.itemAnimator = DefaultItemAnimator()
        }
    }

    override fun init() {
        binding.viewModel = viewModel
        binding.dialogClose.setOnClickListener { dismiss() }

        adapter = TempSaveAdapter({ pos ->
            TempSaveDeleteDialogFragment(pos).show(
                requireActivity().supportFragmentManager, "TempSaveFragment"
            )
        }, { selectedPos ->
            viewModel.setTempSaveListIndex(selectedPos)
        })

        binding.tempSaveList.adapter = adapter
        binding.tempSaveList.layoutManager = LinearLayoutManager(requireContext())

        binding.buttonTempSelect.setOnClickListener {
            viewModel.setUiStatus(NewsAddViewModel.UiStatus.SELECT_TEMP)
            dismiss()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStatus.collect { status ->
                    when (status) {
                        NewsAddViewModel.UiStatus.DELETE_TEMP -> deleteItem(viewModel.tempSaveListIndex.value)
                        else -> {}
                    }
                }
            }
        }
    }
}