package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeWithDeals(
    @Embedded val employee: Employee,
    @Relation(parentColumn = "id", entityColumn = "employeeId") val deals: List<Deal>,
) : Parcelable
