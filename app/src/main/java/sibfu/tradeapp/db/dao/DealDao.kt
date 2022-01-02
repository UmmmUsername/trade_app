package sibfu.tradeapp.db.dao

import androidx.room.Dao
import androidx.room.Query
import sibfu.tradeapp.db.entities.Deal

@Dao
interface DealDao {

    @Query("SELECT * FROM Deal")
    suspend fun findAll(): Array<Deal>
}
