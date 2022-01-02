package sibfu.tradeapp.screens.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLoginBinding
import sibfu.tradeapp.utils.PreferenceKeys
import sibfu.tradeapp.utils.inputText
import sibfu.tradeapp.utils.preferences
import sibfu.tradeapp.utils.removeErrorOnFocus
import sibfu.tradeapp.utils.showShortToast

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: LoginViewModel by viewModels()

        with(FragmentLoginBinding.bind(view)) {
            removeErrorOnFocus(loginLayout, passwordLayout)

            loginButton.setOnClickListener {
                view.clearFocus()

                onContinueButtonPressed(
                    loginLayout = loginLayout,
                    passwordLayout = passwordLayout,
                    logInFunction = viewModel::login,
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.errorMessageRes?.let { errorMessageRes ->
                        showShortToast(messageRes = errorMessageRes)
                        viewModel.onErrorConsumed()
                    }

                    state.employee?.let { employee ->
                        val direction = LoginFragmentDirections.toMainFragment(employee = employee)
                        findNavController().navigate(direction)

                        preferences.edit()
                            .putString(PreferenceKeys.CURRENT_EMPLOYEE, employee.login)
                            .apply()

                        viewModel.onLoggedIn()
                    }
                }
            }
        }
    }

    private fun onContinueButtonPressed(
        loginLayout: TextInputLayout,
        passwordLayout: TextInputLayout,
        logInFunction: (String, String) -> Unit,
    ) {
        val login = loginLayout.inputText
        val password = passwordLayout.inputText

        val areFieldsValid =
            validateLoginFields(
                loginLayout = loginLayout,
                loginText = login,
                passwordLayout = passwordLayout,
                passwordText = password
            )

        if (!areFieldsValid || login == null || password == null) {
            return
        }

        logInFunction(login, password)
    }

    private fun validateLoginFields(
        loginLayout: TextInputLayout,
        loginText: String?,
        passwordLayout: TextInputLayout,
        passwordText: String?,
    ): Boolean {
        val isLoginValid = validateField(layout = loginLayout, text = loginText)
        val isPasswordValid = validateField(layout = passwordLayout, text = passwordText)

        return isLoginValid && isPasswordValid
    }

    private fun validateField(layout: TextInputLayout, text: String?): Boolean {
        return if (text.isNullOrEmpty()) {
            layout.isErrorEnabled = true
            layout.error = getString(R.string.field_required)
            false
        } else {
            true
        }
    }
}
