package sibfu.tradeapp.screens.clients

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.db.entities.Employee

sealed interface ClientsFlow : Parcelable {

    @Parcelize
    class Adapter(val me: Employee) : ClientsFlow

    @Parcelize
    class Choosing(val isSale: Boolean) : ClientsFlow
}
