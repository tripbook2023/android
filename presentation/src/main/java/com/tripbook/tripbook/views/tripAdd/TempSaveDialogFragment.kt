package com.tripbook.tripbook.views.tripAdd

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.tripbook.base.BaseDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.adapter.TempSaveAdapter
import com.tripbook.tripbook.databinding.FragmentTempSaveBinding
import com.tripbook.tripbook.viewmodel.NewsAddViewModel


class TempSaveDialogFragment : BaseDialogFragment<FragmentTempSaveBinding, NewsAddViewModel>(R.layout.fragment_temp_save) {

    override val viewModel: NewsAddViewModel by activityViewModels()
    private lateinit var adapter: TempSaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme)
    }

    override fun init() {
        binding.viewModel = viewModel
        binding.dialogClose.setOnClickListener { dismiss() }

        adapter = TempSaveAdapter ({ pos ->
            TempSaveDeleteDialogFragment(pos).show(
                requireActivity().supportFragmentManager, "TempSaveFragment"
            )
        }, { selectedPos ->
            viewModel.setLocationListIndex(selectedPos)
        })

        binding.tempSaveList.adapter = adapter
        binding.tempSaveList.layoutManager = LinearLayoutManager(requireContext())

        // 임시저장 예제 리스트 -> 나중에 viewModel에서 연결할 예정
//        val list = listOf(
//            TempSaveItem("혼자 가기 좋은 벚꽃 여행지", "23.10.10"),
//            TempSaveItem("제주도에 가면", "23.10.10")
//        )
//        adapter.submitList(list)

        binding.buttonTempSave.setOnClickListener {
            // 선택한 임시저장글 적용 -> viewModel.listIndex 사용
            dismiss()
        }
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
}