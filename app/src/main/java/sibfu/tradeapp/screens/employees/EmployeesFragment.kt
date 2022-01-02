package sibfu.tradeapp.screens.employees

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLinearVerticalRecyclerViewBinding
import sibfu.tradeapp.db.entities.Employee

class EmployeesFragment : Fragment(R.layout.fragment_linear_vertical_recycler_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val employees = arguments?.getParcelableArray(EMPLOYEES) as? Array<Employee> ?: return

        with(FragmentLinearVerticalRecyclerViewBinding.bind(view)) {
            recyclerView.adapter = EmployeesAdapter(employees = employees)
        }
    }

    companion object {
        const val EMPLOYEES = "employees"
    }
}
