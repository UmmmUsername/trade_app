package sibfu.tradeapp.db.entities

import androidx.room.Embedded
import androidx.room.Relation
import sibfu.tradeapp.db.entities.Deal
import sibfu.tradeapp.db.entities.Employee

data class EmployeeWithDeals(
    @Embedded val employee: Employee,
    @Relation(parentColumn = "id", entityColumn = "employeeId") val deals: List<Deal>,
)
