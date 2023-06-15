package com.tripbook.tripbook.core.design

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

enum class ButtonType {
    DEFAULT, DISABLED
}

class TripBookButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
): AppCompatButton(
    context, attributeSet, defStyleAttr
) {
}