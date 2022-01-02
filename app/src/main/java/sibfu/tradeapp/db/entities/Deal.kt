package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Deal(
    @PrimaryKey val id: Int,
    val isPurchase: Boolean,
    val employeeId: Int,
    val clientId: Int,
    val productId: Int,
    val price: Int,
) : Parcelable
