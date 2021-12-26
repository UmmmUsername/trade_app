package sibfu.tradeapp.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentRegisterBinding.bind(view)) {
            val additionalValidation = {
                validateConfirmation(
                    rawPassword = passwordTextInputLayout.editText?.text?.toString(),
                    confirmationLayout = confirmationTextInputLayout
                )
            }

            removeErrorOnFocus(loginTextInputLayout, passwordTextInputLayout)

            loginButton.setOnClickListener {
                view.clearFocus()

                onContinueButtonPressed(
                    loginLayout = loginTextInputLayout,
                    passwordLayout = passwordTextInputLayout,
                    additionalValidation = additionalValidation,
                )
            }
        }
    }

    private fun validateConfirmation(
        rawPassword: String?,
        confirmationLayout: TextInputLayout,
    ): Boolean {
        val password = rawPassword ?: return false
        val confirmation = confirmationLayout.editText?.text?.toString()

        if (!validateField(layout = confirmationLayout, text = confirmation)) {
            return false
        }

        return if (password == confirmation) {
            true
        } else {
            setCustomError(layout = confirmationLayout, errorRes = R.string.passwords_not_match)
            false
        }
    }
}
