package sibfu.tradeapp.screens.employees

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.ItemEmployeeBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.models.Role

class EmployeeViewHolder(
    private val binding: ItemEmployeeBinding,
    private val onClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(employee: Employee) {
        with(binding) {
            val context = root.context

            dispatcherTextView.isVisible = employee.role == Role.DISPATCHER
            nameTextView.text = employee.getShortenedName(context = context)
            positionTextView.text = employee.position
            numberTextView.text = context.getString(R.string.personal_number_pattern, employee.id)

            root.setOnClickListener { onClick(employee.id) }
        }
    }
}
