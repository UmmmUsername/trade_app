package sibfu.tradeapp.screens.employee.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentAddEmployeeBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.utils.inputText
import sibfu.tradeapp.utils.navigateUp
import sibfu.tradeapp.utils.removeErrorOnFocus
import sibfu.tradeapp.utils.validateInput
import java.time.LocalDateTime
import java.time.ZoneOffset

class AddEmployeeFragment : Fragment(R.layout.fragment_add_employee) {

    private val binding by viewBinding(FragmentAddEmployeeBinding::bind)
    private val viewModel: AddEmployeeViewModel by viewModels()

    private val layouts: Array<TextInputLayout>
        get() = with(binding) {
            arrayOf(lastNameLayout, firstNameLayout, positionLayout, loginLayout, passwordLayout)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            removeErrorOnFocus(layouts = layouts)

            toolbar.setNavigationOnClickListener { navigateUp() }

            button.setOnClickListener { onSaveClicked() }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.button.isEnabled = !state.isLoading

                    if (state.isFinished) {
                        navigateUp()
                    }
                }
            }
        }
    }

    private fun onSaveClicked() {
        if (!validateInput(layouts = layouts)) {
            return
        }

        with(binding) {
            val lastName = lastNameEditText.inputText ?: return
            val firstName = firstNameEditText.inputText ?: return
            val position = positionEditText.inputText ?: return
            val login = loginEditText.inputText ?: return
            val password = passwordEditText.inputText ?: return
            val timestamp = LocalDateTime.now().toEpochSecond((ZoneOffset.UTC)) * 1000
            val patronymicRaw = patronymicEditText.inputText
            val patronymic = if (patronymicRaw?.isBlank() != false) null else patronymicRaw.trim()

            val employee = Employee(
                login = login.trim(),
                password = password.trim(),
                firstName = firstName.trim(),
                lastName = lastName.trim(),
                patronymic = patronymic,
                position = position.trim(),
                workSinceTimestamp = timestamp,
                roleString = Employee.WORKER,
                isActive = true,
            )

            viewModel.createEmployee(employee = employee)
        }
    }
}
