package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.temporal.TemporalAmount

@Entity
@Parcelize
data class Deal(
    @PrimaryKey val id: Int,
    val employeeId: Int,
    val clientId: Int,
    val productId: Int,
    val amount: Int,
) : Parcelable
