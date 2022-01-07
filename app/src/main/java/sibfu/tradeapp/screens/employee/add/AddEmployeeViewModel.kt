package sibfu.tradeapp.screens.employee.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.utils.db

class AddEmployeeViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = AddEmployeeState())
    val state: StateFlow<AddEmployeeState> = _state

    fun createEmployee(employee: Employee) {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            db.employeeDao().insert(employee = employee)
            _state.value = state.value.copy(isLoading = false, isFinished = true)
        }
    }
}
