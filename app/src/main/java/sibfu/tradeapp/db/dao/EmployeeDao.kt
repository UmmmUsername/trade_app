package sibfu.tradeapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.EmployeeWithDeals

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM Employee WHERE login = :login")
    suspend fun findEmployeeByLogin(login: String): Employee?

    @Query("SELECT * FROM Employee WHERE roleString != \"${Employee.ADMIN}\"")
    suspend fun findAllNonAdmin(): Array<Employee>

    @Transaction
    @Query("SELECT * FROM Employee WHERE id = :id")
    suspend fun getEmployeeWithDealsById(id: Int): EmployeeWithDeals?

    @Insert
    suspend fun insertAll(vararg users: Employee)

    @Delete
    suspend fun delete(user: Employee)
}
