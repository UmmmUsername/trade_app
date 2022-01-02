package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.models.Role

@Parcelize
@Entity(indices = [Index(value = ["login"], unique = true)])
data class Employee(
    @PrimaryKey val id: Int,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val position: String,
    val workSinceTimestamp: Long,
    val roleString: String,
    // TODO add trades
) : Parcelable {

    val role: Role
        get() = when (roleString) {
            ADMIN -> Role.ADMIN
            DISPATCHER -> Role.DISPATCHER
            WORKER -> Role.WORKER
            else -> throw IllegalStateException("Invalid role for employee with ID = $id")
        }

    companion object {
        const val ADMIN = "admin"
        const val DISPATCHER = "dispatcher"
        const val WORKER = "worker"
    }
}
