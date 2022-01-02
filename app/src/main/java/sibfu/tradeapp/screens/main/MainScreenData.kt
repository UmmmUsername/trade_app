package sibfu.tradeapp.screens.main

import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Deal
import sibfu.tradeapp.db.entities.Employee

sealed interface MainScreenData

data class AdminData(
    val employees: Array<Employee>,
    val clients: Array<Client>,
    val deals: Array<Deal>,
) : MainScreenData {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdminData

        if (!employees.contentEquals(other.employees)) return false
        if (!clients.contentEquals(other.clients)) return false
        if (!deals.contentEquals(other.deals)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = employees.contentHashCode()
        result = 31 * result + clients.contentHashCode()
        result = 31 * result + deals.contentHashCode()
        return result
    }
}
