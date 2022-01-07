package sibfu.tradeapp.screens.employees

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentCommonRecyclerBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.KEY_ME
import sibfu.tradeapp.screens.main.MainFragmentDirections

class EmployeesFragment : Fragment(R.layout.fragment_common_recycler) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val employees = arguments?.getParcelableArray(EMPLOYEES) as? Array<Employee> ?: return
        val me = arguments?.getParcelable<Employee>(KEY_ME) ?: return

        val adapter = EmployeesAdapter(employees = employees) { employeeId ->
            onEmployeeClicked(employeeId = employeeId, myRole = me.role)
        }

        with(FragmentCommonRecyclerBinding.bind(view)) {
            recyclerView.adapter = adapter
            floatingActionButton.isVisible = me.role == Role.DISPATCHER && me.isActive
            floatingActionButton.setOnClickListener { moveToEmployeeAdding() }
        }
    }

    private fun onEmployeeClicked(employeeId: Int, myRole: Role) {
        val direction = MainFragmentDirections.toEmployee(employeeId = employeeId, myRole = myRole)
        findNavController().navigate(direction)
    }

    private fun moveToEmployeeAdding() {
        val direction = MainFragmentDirections.toEmployeeAdding()
        findNavController().navigate(direction)
    }

    companion object {
        const val EMPLOYEES = "employees"
    }
}
