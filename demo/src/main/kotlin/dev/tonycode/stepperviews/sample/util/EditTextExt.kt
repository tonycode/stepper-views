package dev.tonycode.stepperviews.sample.util

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText


/**
 * Fired when text was changed by UI, not programmatically by [EditText.setText] method
 */
fun EditText.onTextChangedFromUi(
    action: (text: String) -> Unit
) {
    setOnEditorActionListener { editText, actionId, _ ->
        when {
            (actionId == EditorInfo.IME_ACTION_DONE) -> {
                action.invoke(editText.text.toString())
                false
            }

            else -> false
        }
    }

    onFocusChangeListener = View.OnFocusChangeListener { editText, hasFocus ->
        if (!hasFocus && (editText is EditText)) {
            action.invoke(editText.text.toString())
        }
    }
}
