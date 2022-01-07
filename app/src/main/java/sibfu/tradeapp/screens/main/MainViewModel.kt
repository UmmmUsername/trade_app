package sibfu.tradeapp.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.utils.db

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = MainState())
    val state: StateFlow<MainState> = _state

    fun requestAdminData() {
        requestAdminDispatcherData { db.employeeDao().findAllNonAdmin() }
    }

    fun requestDispatcherData() {
        requestAdminDispatcherData { db.employeeDao().findAllWorkers() }
    }

    fun requestWorkerData() {
        viewModelScope.launch {
            val deals = db.dealDao().findAll()
            val clients = db.clientDao().findAll()
            val products = db.productDao().findAll()

            val data = WorkerData(fullDeals = deals, clients = clients, products = products)
            _state.value = MainState(isLoading = false, data = data)
        }
    }

    private fun requestAdminDispatcherData(findEmployees: suspend () -> Array<Employee>) {
        viewModelScope.launch {
            val employees = findEmployees()
            val clients = db.clientDao().findAll()
            val deals = db.dealDao().findAll()

            val data =
                AdminDispatcherData(employees = employees, clients = clients, fullDeals = deals)

            _state.value = MainState(isLoading = false, data = data)
        }
    }
}
