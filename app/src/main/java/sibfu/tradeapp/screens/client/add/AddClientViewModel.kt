package sibfu.tradeapp.screens.client.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.utils.db

class AddClientViewModel : ViewModel() {

    fun save(name: String, legalAddress: String, onSaved: () -> Unit) {
        val client = Client(name = name, legalAddress = legalAddress)

        viewModelScope.launch {
            db.clientDao().insert(client = client)
            onSaved()
        }
    }
}
