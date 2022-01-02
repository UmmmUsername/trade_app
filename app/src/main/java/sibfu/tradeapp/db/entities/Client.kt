package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Client(
    @PrimaryKey val id: Int,
    val name: String,
    val legalAddress: String,
) : Parcelable
