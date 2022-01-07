package sibfu.tradeapp.screens.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.db.entities.Employee

sealed interface ProductsFlow : Parcelable {

    @Parcelize
    class Adapter(val me: Employee) : ProductsFlow

    @Parcelize
    class Choosing(val isSale: Boolean) : ProductsFlow
}
