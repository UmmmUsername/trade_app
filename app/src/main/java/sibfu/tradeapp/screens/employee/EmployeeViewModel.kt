package sibfu.tradeapp.screens.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.utils.db

class EmployeeViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = EmployeeState())
    val state: StateFlow<EmployeeState> = _state

    fun requestEmployee(employeeId: Int) {
        viewModelScope.launch {
            val employeeWithDeals = db.employeeDao().getEmployeeWithDealsById(id = employeeId)

            _state.value =
                if (employeeWithDeals == null) {
                    EmployeeState(errorRes = R.string.employee_not_found)
                } else {
                    EmployeeState(isLoading = false, employeeWithDeals = employeeWithDeals)
                }
        }
    }

    fun onErrorConsumed() {
        _state.value = state.value.copy(errorRes = null)
    }
}
