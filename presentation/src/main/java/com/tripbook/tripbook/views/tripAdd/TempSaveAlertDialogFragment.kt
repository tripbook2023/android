package com.tripbook.tripbook.views.tripAdd

import com.tripbook.base.BasePopupDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.DialogBasePopupBinding

class TempSaveAlertDialogFragment :
    BasePopupDialogFragment<DialogBasePopupBinding>(R.layout.dialog_base_popup) {

    override fun setText() {
        // DialogItem을 통해 필수 및 선택 데이터 전달
        val dialogText = DialogItem(
            resources.getString(R.string.temp_save_popup_title),
            resources.getString(R.string.temp_save_popup_content),
            resources.getString(R.string.check),
            null
        )
        binding.baseDialog = dialogText
    }

    override fun setClickListener() {
        binding.btnPositive.setOnClickListener {
            this.dismiss()
        }
    }
}