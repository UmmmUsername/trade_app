package sibfu.tradeapp.db.dao

import androidx.room.Dao
import androidx.room.Query
import sibfu.tradeapp.db.entities.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    suspend fun findAll(): Array<Product>
}
