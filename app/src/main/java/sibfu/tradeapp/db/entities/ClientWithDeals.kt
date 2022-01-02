package sibfu.tradeapp.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ClientWithDeals(
    @Embedded val client: Client,
    @Relation(parentColumn = "id", entityColumn = "clientId") val deals: List<Deal>,
)
