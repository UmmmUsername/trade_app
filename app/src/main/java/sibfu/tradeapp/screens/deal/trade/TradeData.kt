package sibfu.tradeapp.screens.deal.trade

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.Product

@Parcelize
data class TradeData(
    val isSale: Boolean,
    val me: Employee,
    val clients: Array<Client>,
    val products: Array<Product>,
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TradeData

        if (isSale != other.isSale) return false
        if (me != other.me) return false
        if (!clients.contentEquals(other.clients)) return false
        if (!products.contentEquals(other.products)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isSale.hashCode()
        result = 31 * result + me.hashCode()
        result = 31 * result + clients.contentHashCode()
        result = 31 * result + products.contentHashCode()
        return result
    }
}
