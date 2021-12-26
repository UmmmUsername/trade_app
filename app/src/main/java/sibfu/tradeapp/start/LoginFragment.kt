package sibfu.tradeapp.start

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentLoginBinding.bind(view)) {
            removeErrorOnFocus(loginTextInputLayout, passwordTextInputLayout)

            loginButton.setOnClickListener {
                view.clearFocus()

                onContinueButtonPressed(
                    loginLayout = loginTextInputLayout,
                    passwordLayout = passwordTextInputLayout,
                )
            }
        }
    }

    private fun showUnknownUserToast() {
        Toast.makeText(requireContext(), R.string.unknown_login, Toast.LENGTH_SHORT).show()
    }
}
