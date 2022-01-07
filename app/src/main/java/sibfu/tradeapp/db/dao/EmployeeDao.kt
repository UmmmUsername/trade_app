package sibfu.tradeapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import sibfu.tradeapp.db.entities.Employee

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM Employee WHERE login = :login")
    suspend fun findEmployeeByLogin(login: String): Employee?

    @Query("SELECT * FROM Employee WHERE roleString != \"${Employee.ADMIN}\" ORDER BY isActive DESC")
    suspend fun findAllNonAdmin(): Array<Employee>

    @Query("SELECT * FROM Employee WHERE roleString == \"${Employee.WORKER}\" ORDER BY isActive DESC")
    suspend fun findAllWorkers(): Array<Employee>

    @Query("SELECT * FROM Employee WHERE id = :id")
    suspend fun findEmployeeById(id: Int): Employee?

    @Update
    suspend fun update(employee: Employee)

    @Insert
    suspend fun insert(employee: Employee)
}
