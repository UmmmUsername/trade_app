package sibfu.tradeapp

import android.app.Application
import androidx.room.Room
import sibfu.tradeapp.db.Database

class TradeApplication : Application() {

    lateinit var db: Database

    override fun onCreate() {
        super.onCreate()

        instance = this
        db = Room
            .databaseBuilder(applicationContext, Database::class.java, "Database")
            .createFromAsset("database/employee.db")
            .build()
    }

    companion object {
        lateinit var instance: TradeApplication
            private set
    }
}
