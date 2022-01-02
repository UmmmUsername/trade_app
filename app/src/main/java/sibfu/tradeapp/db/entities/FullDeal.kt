package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullDeal(
    @Embedded val deal: Deal,
    @Relation(parentColumn = "employeeId", entityColumn = "id") val employee: Employee,
    @Relation(parentColumn = "clientId", entityColumn = "id") val client: Client,
    @Relation(parentColumn = "productId", entityColumn = "id") val product: Product,
) : Parcelable
