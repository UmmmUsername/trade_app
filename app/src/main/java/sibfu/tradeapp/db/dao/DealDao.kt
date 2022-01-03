package sibfu.tradeapp.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import sibfu.tradeapp.db.entities.FullDeal

@Dao
interface DealDao {

    @Transaction
    @Query("SELECT * FROM Deal")
    suspend fun findAll(): Array<FullDeal>

    @Transaction
    @Query("SELECT * FROM Deal WHERE employeeId = :employeeId")
    suspend fun findAllByEmployeeId(employeeId: Int): Array<FullDeal>
}
