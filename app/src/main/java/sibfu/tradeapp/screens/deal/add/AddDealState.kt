package sibfu.tradeapp.screens.deal.add

import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Product

data class AddDealState(
    val isLoading: Boolean = true,
    val clientsToProducts: Pair<Array<Client>, Array<Product>>? = null,
)
