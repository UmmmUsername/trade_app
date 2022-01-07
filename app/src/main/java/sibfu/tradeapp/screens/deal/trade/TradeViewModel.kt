package sibfu.tradeapp.screens.deal.trade

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Deal
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.utils.db

class TradeViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = TradeState())
    val state: StateFlow<TradeState> = _state

    fun selectClient(client: Client) {
        _state.value = state.value.copy(client = client)
    }

    fun selectProduct(product: Product) {
        _state.value = state.value.copy(product = product)
    }

    fun updateAmount(amount: Int?) {
        _state.value = state.value.copy(amount = amount)
    }

    fun save(isSale: Boolean, employee: Employee, onSaved: () -> Unit) {
        val (client, product, amount) = state.value

        if (client == null) {
            _state.value = state.value.copy(errorRes = R.string.need_to_choose_client)
            return
        }

        if (product == null) {
            _state.value = state.value.copy(errorRes = R.string.need_to_choose_product)
            return
        }

        if (amount == null) {
            _state.value = state.value.copy(errorRes = R.string.need_to_choose_amount)
            return
        }

        viewModelScope.launch {
            val factor = if (isSale) 1 else -1

            val deal = Deal(
                employeeId = employee.id,
                clientId = client.id,
                productId = product.id,
                amount = amount * factor
            )

            db.dealDao().insertDeal(deal = deal)
            onSaved()
        }
    }

    fun onErrorConsumed() {
        _state.value = state.value.copy(errorRes = null)
    }
}
