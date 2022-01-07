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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentEmployeeBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.deals.DealsAdapter
import sibfu.tradeapp.utils.navigateUp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class EmployeeFragment : Fragment(R.layout.fragment_employee) {

    private val args: EmployeeFragmentArgs by navArgs()
    private val viewModel: EmployeeViewModel by viewModels()

    private val binding by viewBinding(FragmentEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.requestEmployee(employeeId = args.employeeId)

        with(binding.toolbar) {
            setNavigationOnClickListener { navigateUp() }
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
                                recyclerView.adapter =
                                    DealsAdapter(fullDeals = fullDeals) { fullDeal ->
                                        moveToDealFragment(
                                            fullDeal = fullDeal,
                                            myRole = args.myRole,
                                        )
                                    }
                            }
                        }
                    }
                }
            }
        }
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

            val statusRes = if (employee.isActive) R.string.status_active else R.string.status_fired
            statusView.descriptionTextView.text = getString(statusRes)

            dispatcherTextCheckBoxView.checkBox.isEnabled = employee.isActive
            dispatcherTextCheckBoxView.checkBox.isChecked = employee.role == Role.DISPATCHER
            dispatcherTextCheckBoxView.checkBox.setOnCheckedChangeListener { _, isChecked ->
                viewModel.changeRole(isDispatcher = isChecked)
            }

            when (args.myRole) {
                Role.ADMIN -> {
                    dispatcherTextCheckBoxView.isVisible = true
                    button.text = getString(R.string.save)
                    button.setOnClickListener { viewModel.save { navigateUp() } }
                }

                Role.DISPATCHER -> {
                    val isActive = employee.isActive

                    val buttonTextRes =
                        if (employee.isActive) {
                            R.string.fire_employee
                        } else {
                            R.string.return_employee
                        }

                    button.text = getString(buttonTextRes)
                    button.setOnClickListener {
                        showEmployeeStatusChangingDialog(isFired = isActive)
                    }
                }

                Role.WORKER -> {
                    button.isVisible = false
                }
            }
        }
    }

    private fun showEmployeeStatusChangingDialog(isFired: Boolean) {
        val messageRes =
            if (isFired) R.string.fire_employee_message else R.string.return_employee_message

        MaterialAlertDialogBuilder(requireContext())
            .setMessage(messageRes)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.changeEmployeeStatus(isFired = isFired)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

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

    private fun moveToDealFragment(fullDeal: FullDeal, myRole: Role) {
        val direction = EmployeeFragmentDirections.toDealFragment(
            fullDeal = fullDeal,
            myRole = myRole,
        )

        findNavController().navigate(direction)
    }
}
