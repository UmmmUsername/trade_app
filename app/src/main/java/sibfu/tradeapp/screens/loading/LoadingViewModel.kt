package sibfu.tradeapp.screens.loading

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sibfu.tradeapp.utils.PreferenceKeys
import sibfu.tradeapp.utils.db

class LoadingViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = LoadingState())
    val state: StateFlow<LoadingState> = _state

    fun requestEmployee(preferences: SharedPreferences) {
        viewModelScope.launch {
            val login = withContext(Dispatchers.Default) {
                preferences.getString(PreferenceKeys.CURRENT_EMPLOYEE, null)
            }

            val employee =
                if (login == null) {
                    null
                } else {
                    db.employeeDao().findEmployeeByLogin(login = login)
                }

            _state.value = LoadingState(isLoading = false, employee = employee)
        }
    }
}
