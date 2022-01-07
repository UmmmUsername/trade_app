package sibfu.tradeapp.screens.deal.trade

import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Product

data class TradeState(
    val client: Client? = null,
    val product: Product? = null,
    val amount: Int? = null,
    val errorRes: Int? = null,
)
