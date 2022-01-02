package sibfu.tradeapp.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.utils.db

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = LoginState())
    val state: StateFlow<LoginState> = _state

    fun login(login: String, password: String) {
        viewModelScope.launch {
            val employee = db.employeeDao().findEmployeeByLogin(login = login)

            _state.value =
                if (employee == null || employee.password != password) {
                    state.value.copy(errorMessageRes = R.string.login_and_password_not_match)
                } else {
                    state.value.copy(employee = employee)
                }
        }
    }

    fun onLoggedIn() {
        _state.value = state.value.copy(employee = null)
    }

    fun onErrorConsumed() {
        _state.value = state.value.copy(errorMessageRes = null)
    }
}
