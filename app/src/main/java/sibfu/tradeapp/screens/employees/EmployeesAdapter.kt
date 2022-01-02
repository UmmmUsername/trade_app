package sibfu.tradeapp.screens.employees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.databinding.ItemEmployeeBinding
import sibfu.tradeapp.db.entities.Employee

class EmployeesAdapter(
    private val employees: Array<Employee>,
) : RecyclerView.Adapter<EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return EmployeeViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employee = employees[position])
    }

    override fun getItemCount(): Int =
        employees.size
}
