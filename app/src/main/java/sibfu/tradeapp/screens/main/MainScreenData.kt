package sibfu.tradeapp.screens.main

import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.db.entities.Product

sealed interface MainScreenData

data class AdminDispatcherData(
    val employees: Array<Employee>,
    val clients: Array<Client>,
    val fullDeals: Array<FullDeal>,
) : MainScreenData {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AdminDispatcherData

        if (!employees.contentEquals(other.employees)) return false
        if (!clients.contentEquals(other.clients)) return false
        if (!fullDeals.contentEquals(other.fullDeals)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = employees.contentHashCode()
        result = 31 * result + clients.contentHashCode()
        result = 31 * result + fullDeals.contentHashCode()
        return result
    }
}

data class WorkerData(
    val fullDeals: Array<FullDeal>,
    val clients: Array<Client>,
    val products: Array<Product>,
) : MainScreenData {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WorkerData

        if (!fullDeals.contentEquals(other.fullDeals)) return false
        if (!clients.contentEquals(other.clients)) return false
        if (!products.contentEquals(other.products)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fullDeals.contentHashCode()
        result = 31 * result + clients.contentHashCode()
        result = 31 * result + products.contentHashCode()
        return result
    }
}
