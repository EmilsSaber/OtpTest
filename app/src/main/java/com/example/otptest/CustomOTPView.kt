package com.example.otptest

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout

class CustomOTPView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var editTextList: List<EditText>

    init {
        inflate(context, R.layout.otp_input_view, this)
        editTextList = listOf(
            findViewById(R.id.editText1),
            findViewById(R.id.editText2),
            findViewById(R.id.editText3),
            findViewById(R.id.editText4)
        )

        setupEditTextListeners()
    }

    private fun setupEditTextListeners() {
        for (i in editTextList.indices) {
            val editText = editTextList[i]

            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        focusNextEditText(i)
                        if (i == editTextList.size - 1) {
                            editTextList[i].clearFocus()
                        }
                    } else if (s?.isEmpty() == true) {
                        focusPreviousEditText(i)
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (editText.text.isNotEmpty()) {
                        editText.selectAll()
                    } else {
                        editText.hint = ""
                    }
                } else {
                    if (editText.text.isEmpty()) {
                        editText.hint = "\n__\n  "
                    }
                }

                if (hasFocus && editText.text.isNotEmpty()) {
                    editText.text.clear()
                }
            }
            editText.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (i > 0) {
                        if (editTextList[i - 1].text.isNotEmpty()) {
                            editTextList[i - 1].text.clear()
                            editTextList[i - 1].requestFocus()
                        } else {
                            clearPreviousEditTexts(i)
                            focusPreviousEditText(i)
                        }
                    }
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun clearPreviousEditTexts(index: Int) {
        for (i in 0 until index) {
            editTextList[i].text.clear()
        }
    }

    private fun focusNextEditText(index: Int) {
        if (index < editTextList.size - 1) {
            editTextList[index + 1].requestFocus()
        } else {
            editTextList[index].clearFocus()
            hideSoftKeyboard()
        }
    }

    private fun focusPreviousEditText(index: Int) {
        if (index > 0 && editTextList[index - 1].text.isEmpty()) {
            editTextList[index - 1].requestFocus()
        }
    }


    @SuppressLint("ServiceCast")
    private fun hideSoftKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}