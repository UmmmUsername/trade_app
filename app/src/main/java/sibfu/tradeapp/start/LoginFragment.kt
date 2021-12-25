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
            loginButton.setOnClickListener {
                loginTextInputEditText.text?.toString()?.let { login -> moveToNext(login = login) }
            }
        }
    }

    private fun moveToNext(login: String) {
        when (login) {
            ADMIN_LOGIN -> {
            }

            DISPATCHER_LOGIN -> {
            }

            WORKER_LOGIN -> {
            }

            else -> showUnknownUserToast()
        }
    }

    private fun showUnknownUserToast() {
        Toast.makeText(requireContext(), R.string.unknown_login, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ADMIN_LOGIN = "admin"
        private const val DISPATCHER_LOGIN = "dispatcher"
        private const val WORKER_LOGIN = "worker"
    }
}
