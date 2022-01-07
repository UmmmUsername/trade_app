package sibfu.tradeapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import sibfu.tradeapp.db.dao.ClientDao
import sibfu.tradeapp.db.dao.DealDao
import sibfu.tradeapp.db.dao.EmployeeDao
import sibfu.tradeapp.db.dao.ProductDao
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Deal
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.Product

@Database(
    entities = [
        Employee::class,
        Client::class,
        Deal::class,
        Product::class
    ], version = 1
)
abstract class Database : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    abstract fun clientDao(): ClientDao

    abstract fun dealDao(): DealDao

    abstract fun productDao(): ProductDao
}
