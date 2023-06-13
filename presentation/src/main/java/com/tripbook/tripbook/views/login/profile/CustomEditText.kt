package com.tripbook.tripbook.views.login.profile

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.tripbook.tripbook.R

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher, View.OnTouchListener,
    View.OnFocusChangeListener {

    private var focusChangeListener: OnFocusChangeListener? = null
    private var touchListener: OnTouchListener? = null
    private var editorActionListener: OnEditorActionListener? = null
    private var errorMsg: String? = ""

    init {
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
    }

    override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    fun isNicknameValid(nickname: CharSequence): String? {
        // 닉네임 조건: 영문, 한글, 숫자를 포함하는 10글자 이내 문자열(자음, 모음, 공백 X)
        val regex = Regex("^[0-9a-zA-Z가-힣]+\$")
        return if (nickname.length > 10) {
            setError(resources.getString(R.string.nickname_length_alert))
        } else if (!nickname.matches(regex)) {
            setError(resources.getString(R.string.nickname_sign_alert))
        } else {
            setError(null)
        }
    }

    fun isAgeValid(age: CharSequence): String? {
        val regex = Regex("^[1-9]{1}[0-9]{1}$")
        return if (!age.matches(regex)) {
            setError("10~100 사이의 숫자를 입력해주세요")
        } else {
            setError(null)
        }
    }

    fun setError(str: String?): String? {
        if (str == null) {
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
            errorMsg = null
        } else {
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.warning)
            errorMsg = str
        }
        return errorMsg
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.focusChangeListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.touchListener = onTouchListener
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.x

        if (this.compoundDrawables[2] != null && x >= width - paddingRight - this.compoundDrawables[2].intrinsicWidth) {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                error = null
                text = null
            }
            return true
        }

        return touchListener?.onTouch(view, motionEvent) ?: false
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        focusChangeListener?.onFocusChange(view, hasFocus)
    }

    override fun setOnEditorActionListener(onEditorActionListener: OnEditorActionListener?) {
        this.editorActionListener = onEditorActionListener
    }

    override fun onEditorAction(actionCode: Int) {
        if (actionCode == EditorInfo.IME_ACTION_DONE) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }
}