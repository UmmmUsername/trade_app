package sibfu.tradeapp.screens.employee

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentEmployeeBinding
import sibfu.tradeapp.db.entities.EmployeeWithDeals

class EmployeeFragment : Fragment(R.layout.fragment_employee) {

    private val args: EmployeeFragmentArgs by navArgs()

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEmployeeBinding.bind(view)
        val viewModel: EmployeeViewModel by viewModels()
        viewModel.requestEmployee(employeeId = args.employeeId)

        with(binding.toolbar) {
            setNavigationOnClickListener { navigateUp() }
            title = getString(R.string.about_employee)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.setLoading(value = state.isLoading)

                    state.errorRes?.let { errorRes ->
                        showErrorDialog(messageRes = errorRes)
                        viewModel.onErrorConsumed()
                    }

                    state.employeeWithDeals?.let { employeeWithDeals ->
                        showEmployee(employeeWithDeals = employeeWithDeals)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentEmployeeBinding.setLoading(value: Boolean) {
        progress.isVisible = value
        scrollView.isVisible = !value
        button.isVisible = !value
    }

    private fun showErrorDialog(messageRes: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(messageRes)
            .setPositiveButton(R.string.ok) { _, _ -> navigateUp() }
            .show()
    }

    private fun showEmployee(employeeWithDeals: EmployeeWithDeals) {
        val (employee, deals) = employeeWithDeals

        with(binding) {
            lastNameView.titleTextView.text = getString(R.string.lastName)
            lastNameView.descriptionTextView.text = employee.lastName

            firstNameView.titleTextView.text = getString(R.string.firstName)
            firstNameView.descriptionTextView.text = employee.firstName

            employee.patronymic?.let { patronymic ->
                patronymicView.titleTextView.text = getString(R.string.patronymic)
                patronymicView.descriptionTextView.text = patronymic
            }
        }
    }

    private fun navigateUp(): Boolean =
        findNavController().navigateUp()
}
