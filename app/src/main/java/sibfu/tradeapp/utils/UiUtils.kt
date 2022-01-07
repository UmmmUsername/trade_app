package sibfu.tradeapp.utils

import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import sibfu.tradeapp.R

val EditText.inputText: String?
    get() = text?.toString()

val TextInputLayout.inputText: String?
    get() = editText?.inputText

fun setRemoveErrorOnFocusListener(vararg layouts: TextInputLayout) {
    layouts.forEach { layout ->
        layout.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                layout.error = null
                layout.isErrorEnabled = false
            }
        }
    }
}

fun Fragment.validateInput(vararg layouts: TextInputLayout): Boolean {
    return layouts.map { layout -> validateInput(layout) }
        .reduce { isFirstValid, isSecondValid -> isFirstValid && isSecondValid }
}

fun Fragment.validateInput(layout: TextInputLayout): Boolean {
    return if (layout.inputText.isNullOrEmpty()) {
        layout.isErrorEnabled = true
        layout.error = getString(R.string.field_required)
        false
    } else {
        true
    }
}

fun Fragment.showShortToast(messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}

fun EditText.restrictByIntBounds() {
    val filter = InputFilter { source, _, _, dest, _, _ ->
        val string = dest?.toString()?.plus(source?.toString() ?: "")
        val number = string?.toIntOrNull()

        if (number == null || number <= 0) {
            ""
        } else {
            null
        }
    }

    filters = arrayOf(filter)
}

fun EditText.setNumbersOnlyInputType() {
    inputType =
        InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD

    val passwordTransformationMethod = object : PasswordTransformationMethod() {

        override fun getTransformation(source: CharSequence, view: View): CharSequence =
            source
    }

    transformationMethod = passwordTransformationMethod
}
