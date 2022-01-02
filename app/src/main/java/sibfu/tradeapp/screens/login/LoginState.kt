package sibfu.tradeapp.screens.login

import sibfu.tradeapp.db.entities.Employee

data class LoginState(
    val isLoading: Boolean = false,
    val employee: Employee? = null,
    val errorMessageRes: Int? = null,
)
