package sibfu.tradeapp.screens.loading

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import sibfu.tradeapp.db.entities.Employee

@Parcelize
data class LoadingState(
    val isLoading: Boolean = true,
    val employee: Employee? = null,
) : Parcelable
