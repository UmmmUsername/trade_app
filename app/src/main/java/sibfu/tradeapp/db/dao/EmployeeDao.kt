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
    @Query("SELECT * FROM Employee")
    fun getEmployeesWithDeals(): List<EmployeeWithDeals>

    @Insert
    fun insertAll(vararg users: Employee)

    @Delete
    fun delete(user: Employee)
}
