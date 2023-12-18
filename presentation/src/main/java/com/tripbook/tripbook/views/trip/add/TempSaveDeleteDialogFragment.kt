package com.tripbook.tripbook.views.trip.add

import androidx.fragment.app.activityViewModels
import com.tripbook.base.BasePopupDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.DialogBasePopupBinding
import com.tripbook.tripbook.viewmodel.NewsAddViewModel

class TempSaveDeleteDialogFragment(private val pos: Int): BasePopupDialogFragment<DialogBasePopupBinding>(R.layout.dialog_base_popup) {

    val viewModel: NewsAddViewModel by activityViewModels()

    override fun setText() {
        val dialogText = DialogItem(
            resources.getString(R.string.temp_save_delete),
            null,
            resources.getString(R.string.temp_save_delete_button),
            resources.getString(R.string.close)
        )
        binding.baseDialog = dialogText

    }

    override fun setClickListener() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.btnNegative.setOnClickListener {
            this.dismiss()
        }
        binding.btnPositive.setOnClickListener {
            // 임시저장 아이템 삭제
            val tempArticle = viewModel.tempSaveList.value[pos]
            viewModel.setTempSaveListIndex(pos)
            viewModel.deleteArticle(tempArticle.id)
            this.dismiss()
            viewModel.setUiStatus(NewsAddViewModel.UiStatus.DELETE_TEMP)
        }
    }
}