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
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.deals.DealsAdapter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class EmployeeFragment : Fragment(R.layout.fragment_employee) {

    private val args: EmployeeFragmentArgs by navArgs()
    private val viewModel: EmployeeViewModel by viewModels()

    private var _binding: FragmentEmployeeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEmployeeBinding.bind(view)
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

                    state.employee?.let { employee -> showEmployee(employee = employee) }

                    state.fullDeals?.let { fullDeals ->
                        val isNotEmpty = fullDeals.isNotEmpty()

                        with(binding) {
                            dealsTextView.isVisible = isNotEmpty
                            recyclerView.isVisible = isNotEmpty

                            if (isNotEmpty) {
                                recyclerView.adapter = DealsAdapter(
                                    fullDeals = fullDeals,
                                    onDealClicked = ::moveToDealFragment,
                                )
                            }
                        }
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

    private fun showEmployee(employee: Employee) {
        viewModel.requestFullDeals(employeeId = employee.id)

        with(binding) {
            lastNameView.descriptionTextView.text = employee.lastName
            firstNameView.descriptionTextView.text = employee.firstName

            employee.patronymic?.let { patronymic ->
                patronymicView.isVisible = true
                patronymicView.descriptionTextView.text = patronymic
            }

            numberView.descriptionTextView.text = employee.id.toString()
            positionView.descriptionTextView.text = employee.position

            experienceView.descriptionTextView.text =
                getExperienceString(workSinceTimestamp = employee.workSinceTimestamp)

            dispatcherTextCheckBoxView.checkBox.isChecked = employee.role == Role.DISPATCHER
            dispatcherTextCheckBoxView.checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeRole(isDispatcher = isChecked)
            }

            button.text = getString(R.string.save)
            button.setOnClickListener { viewModel.save { navigateUp() } }
        }
    }

    private fun navigateUp(): Boolean =
        findNavController().navigateUp()

    private fun getExperienceString(workSinceTimestamp: Long): String {
        val startDate =
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(workSinceTimestamp),
                ZoneId.systemDefault()
            ).toLocalDate()

        val now = LocalDate.now()
        val years = ChronoUnit.YEARS.between(startDate, now)
        val newLocalDate = startDate.plus(years, ChronoUnit.YEARS)
        val months = ChronoUnit.MONTHS.between(newLocalDate, now).toInt()

        val yearsString =
            if (years > 0) {
                resources.getQuantityString(R.plurals.years, years.toInt(), years)
            } else {
                null
            }

        val monthsString =
            if (months > 0) {
                resources.getQuantityString(R.plurals.months, months, months)
            } else {
                null
            }

        return when {
            yearsString != null && monthsString != null ->
                getString(R.string.space_separated_pattern, yearsString, monthsString)

            yearsString != null -> yearsString
            monthsString != null -> monthsString
            else -> getString(R.string.less_than_month)
        }
    }

    private fun moveToDealFragment(fullDeal: FullDeal) {
        val direction = EmployeeFragmentDirections.toDealFragment()
        findNavController().navigate(direction)
    }
}
