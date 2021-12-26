package sibfu.tradeapp.start

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import sibfu.tradeapp.R

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

fun Fragment.onContinueButtonPressed(
    loginLayout: TextInputLayout,
    passwordLayout: TextInputLayout,
    additionalValidation: (() -> Boolean)? = null,
) {
    val login = loginLayout.editText?.text?.toString()
    val password = passwordLayout.editText?.text?.toString()

    val areFieldsValid =
        validateLoginFields(
            loginLayout = loginLayout,
            loginText = login,
            passwordLayout = passwordLayout,
            passwordText = password
        )

    val isAdditionalValidationPassed = additionalValidation?.invoke() != false

    if (!areFieldsValid || isAdditionalValidationPassed) {
        return
    }

    // TODO request data from DB
}

private fun Fragment.validateLoginFields(
    loginLayout: TextInputLayout,
    loginText: String?,
    passwordLayout: TextInputLayout,
    passwordText: String?,
): Boolean {
    val isLoginValid = validateField(layout = loginLayout, text = loginText)
    val isPasswordValid = validateField(layout = passwordLayout, text = passwordText)

    return isLoginValid && isPasswordValid
}

fun Fragment.validateField(layout: TextInputLayout, text: String?): Boolean {
    return if (text.isNullOrEmpty()) {
        setCustomError(layout = layout, errorRes = R.string.field_required)
        false
    } else {
        true
    }
}

fun Fragment.setCustomError(layout: TextInputLayout, @StringRes errorRes: Int) {
    layout.isErrorEnabled = true
    layout.error = getString(errorRes)
}
