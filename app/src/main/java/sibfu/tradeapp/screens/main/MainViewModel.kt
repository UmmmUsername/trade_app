package sibfu.tradeapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.utils.db

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = MainState())
    val state: StateFlow<MainState> = _state

    fun requestAdminData() {
        viewModelScope.launch {
            val employees = db.employeeDao().findAllNonAdmin()
            val clients = db.clientDao().findAll()
            val deals = db.dealDao().findAll()

            val data = AdminData(employees = employees, clients = clients, deals = deals)

            _state.value = MainState(isLoading = false, data = data)
        }
    }
}
