package sibfu.tradeapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sibfu.tradeapp.db.entities.Client

@Dao
interface ClientDao {

    @Query("SELECT * FROM Client")
    suspend fun findAll(): Array<Client>

    @Insert
    suspend fun insert(client: Client)
}
