package sibfu.tradeapp.screens.employee

import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal

data class EmployeeState(
    val isLoading: Boolean = true,
    val employee: Employee? = null,
    val fullDeals: Array<FullDeal>? = null,
    val errorRes: Int? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmployeeState

        if (isLoading != other.isLoading) return false
        if (employee != other.employee) return false
        if (fullDeals != null) {
            if (other.fullDeals == null) return false
            if (!fullDeals.contentEquals(other.fullDeals)) return false
        } else if (other.fullDeals != null) return false
        if (errorRes != other.errorRes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (employee?.hashCode() ?: 0)
        result = 31 * result + (fullDeals?.contentHashCode() ?: 0)
        result = 31 * result + (errorRes ?: 0)
        return result
    }
}
