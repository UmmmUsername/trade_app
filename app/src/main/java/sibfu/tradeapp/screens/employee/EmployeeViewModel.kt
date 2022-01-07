package sibfu.tradeapp.screens.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.utils.db

class EmployeeViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = EmployeeState())
    val state: StateFlow<EmployeeState> = _state

    fun requestEmployee(employeeId: Int) {
        viewModelScope.launch {
            val employee = db.employeeDao().findEmployeeById(id = employeeId)

            _state.value =
                if (employee == null) {
                    EmployeeState(errorRes = R.string.employee_not_found)
                } else {
                    EmployeeState(isLoading = false, employee = employee)
                }
        }
    }

    fun requestFullDeals(employeeId: Int) {
        viewModelScope.launch {
            val fullDeals = db.dealDao().findAllByEmployeeId(employeeId = employeeId)
            _state.value = state.value.copy(fullDeals = fullDeals)
        }
    }

    fun changeRole(isDispatcher: Boolean) {
        val newEmployee =
            if (isDispatcher) {
                state.value.employee?.copy(roleString = Employee.DISPATCHER)
            } else {
                state.value.employee?.copy(roleString = Employee.WORKER)
            }

        _state.value = state.value.copy(employee = newEmployee)
    }

    fun save(doOnSaved: () -> Unit) {
        viewModelScope.launch {
            state.value.employee?.let { employee -> db.employeeDao().update(employee) }
            doOnSaved()
        }
    }

    fun changeEmployeeStatus(isFired: Boolean) {
        state.value.employee?.copy(isActive = !isFired)?.let { employee ->
            viewModelScope.launch {
                db.employeeDao().update(employee = employee)
                _state.value = state.value.copy(employee = employee)
            }
        }
    }

    fun onErrorConsumed() {
        _state.value = state.value.copy(errorRes = null)
    }
}
