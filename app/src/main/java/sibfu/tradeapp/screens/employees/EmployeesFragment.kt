package sibfu.tradeapp.screens.employees

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLinearVerticalRecyclerViewBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.MY_ROLE_KEY
import sibfu.tradeapp.screens.main.MainFragmentDirections

class EmployeesFragment : Fragment(R.layout.fragment_linear_vertical_recycler_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val employees = arguments?.getParcelableArray(EMPLOYEES) as? Array<Employee> ?: return
        val myRole = arguments?.getSerializable(MY_ROLE_KEY) as? Role ?: return

        val adapter = EmployeesAdapter(employees = employees) { employeeId ->
            onEmployeeClicked(employeeId = employeeId, myRole = myRole)
        }

        with(FragmentLinearVerticalRecyclerViewBinding.bind(view)) {
            recyclerView.adapter = adapter
        }
    }

    private fun onEmployeeClicked(employeeId: Int, myRole: Role) {
        val direction = MainFragmentDirections.toEmployee(employeeId = employeeId, myRole = myRole)
        findNavController().navigate(direction)
    }

    companion object {
        const val EMPLOYEES = "employees"
    }
}
