package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.db.entities.interfaces.HasName

@Entity
@Parcelize
data class Client(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    override val name: String,
    val legalAddress: String,
) : Parcelable, HasName
