package sibfu.tradeapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.db.entities.interfaces.HasName

@Entity
@Parcelize
data class Product(
    @PrimaryKey val id: Int,
    override val name: String,
    val unit: String,
    val price: Int,
) : Parcelable, HasName
