package com.tripbook.tripbook.views.login.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.tripbook.tripbook.R

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher, View.OnTouchListener,
    View.OnFocusChangeListener {

    private var clearDrawable: Drawable? = null
    private var focusChangeListener: OnFocusChangeListener? = null
    private var touchListener: OnTouchListener? = null
    private var editorActionListener: OnEditorActionListener? = null
    var errorMsg: String? = ""

    init {
        // X 버튼 추가, Touch, Focus, TextWatcher 리스너 추가
        val tempDrawable = ContextCompat.getDrawable(
            context,
            com.tripbook.tripbook.core.design.R.drawable.icn_clear_20
        )
        clearDrawable = tempDrawable?.let { DrawableCompat.wrap(it) }
        clearDrawable?.run { setBounds(0, 0, intrinsicWidth, intrinsicHeight) }
        setClearIconVisible(false)

        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }


    private fun setClearIconVisible(visible: Boolean) {
        clearDrawable?.setVisible(visible, false)
        val right: Drawable? = if (visible) clearDrawable else null
        setCompoundDrawables(null, null, right, null)
    }


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        // 텍스트 길이에 따라 X버튼 보이기 / 없애기
        if (isFocused)
            setClearIconVisible(text.isNullOrEmpty().not())
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

        clearDrawable?.run {
            if (isVisible && x > width - paddingRight - intrinsicWidth) {
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    error = null
                    text = null
                }
                return true
            }
        }

        return touchListener?.onTouch(view, motionEvent) ?: false
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(text.isNullOrEmpty().not())
        } else {
            setClearIconVisible(false)
        }

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