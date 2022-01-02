package sibfu.tradeapp.screens.employee

import sibfu.tradeapp.db.entities.EmployeeWithDeals

data class EmployeeState(
    val isLoading: Boolean = true,
    val employeeWithDeals: EmployeeWithDeals? = null,
    val errorRes: Int? = null,
)
