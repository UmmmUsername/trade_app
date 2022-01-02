package sibfu.tradeapp.utils

import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

val EditText.inputText: String?
    get() = text?.toString()

val TextInputLayout.inputText: String?
    get() = editText?.inputText

fun removeErrorOnFocus(vararg layouts: TextInputLayout) {
    layouts.forEach { layout ->
        layout.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                layout.error = null
                layout.isErrorEnabled = false
            }
        }
    }
}

fun Fragment.showShortToast(messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}
