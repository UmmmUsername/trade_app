package sibfu.tradeapp.screens.product.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.utils.db

class AddProductViewModel : ViewModel() {

    fun add(product: Product, onSaved: () -> Unit) {
        viewModelScope.launch {
            db.productDao().insert(product = product)
            onSaved()
        }
    }
}
