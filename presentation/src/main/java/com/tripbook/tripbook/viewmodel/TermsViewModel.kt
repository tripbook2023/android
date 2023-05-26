package com.tripbook.tripbook.viewmodel

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.tripbook.tripbook.views.login.terms.TermsDialogFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TermsViewModel : ViewModel() {

    private var _title = MutableStateFlow("")
    val termsTitle: StateFlow<String> get() = _title

    //checkbox 부분을 ...viewmodel로 얼만큼 갖고 와야 되는지 모르겠어서
    //최대한 갖고 온 부분이 termsTitle인데 .. 핃백 부탁해용 ..

    fun getTermsTitle(title : String) {
        var termsTitle = title.substring(5) //야매방법...^^; ㅋㅋ
            _title.value = termsTitle
    }

}