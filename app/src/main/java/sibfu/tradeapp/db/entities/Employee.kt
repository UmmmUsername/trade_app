package sibfu.tradeapp.db.entities

import android.content.Context
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.R
import sibfu.tradeapp.models.Role

@Parcelize
@Entity(indices = [Index(value = ["login"], unique = true)])
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String?,
    val position: String,
    val workSinceTimestamp: Long,
    val roleString: String,
    val isActive: Boolean,
) : Parcelable {

    val role: Role
        get() = when (roleString) {
            ADMIN -> Role.ADMIN
            DISPATCHER -> Role.DISPATCHER
            WORKER -> Role.WORKER
            else -> throw IllegalStateException("Invalid role for employee with ID = $id")
        }

    fun getShortenedName(context: Context): String {
        val nameFirstLetter = firstName[0]
        val patronymicFirstLetter = patronymic?.get(0)

        return if (patronymicFirstLetter == null) {
            context.getString(R.string.shortened_name_pattern_no_patronymic, lastName, firstName)
        } else {
            context.getString(
                R.string.shortened_name_pattern,
                lastName,
                nameFirstLetter,
                patronymicFirstLetter
            )
        }
    }

    companion object {
        const val ADMIN = "admin"
        const val DISPATCHER = "dispatcher"
        const val WORKER = "worker"
    }
}
