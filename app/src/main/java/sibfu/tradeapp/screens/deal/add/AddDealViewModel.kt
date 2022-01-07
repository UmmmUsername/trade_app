package sibfu.tradeapp.screens.deal.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.utils.db

class AddDealViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = AddDealState())
    val state: StateFlow<AddDealState> = _state

    init {
        viewModelScope.launch {
            val clients = db.clientDao().findAll()
            val products = db.productDao().findAll()

            _state.value = AddDealState(isLoading = false, clientsToProducts = clients to products)
        }
    }
}
