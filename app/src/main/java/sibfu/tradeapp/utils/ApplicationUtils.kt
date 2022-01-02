package sibfu.tradeapp.utils

import sibfu.tradeapp.TradeApplication
import sibfu.tradeapp.db.Database

val db: Database
    get() = TradeApplication.instance.db
