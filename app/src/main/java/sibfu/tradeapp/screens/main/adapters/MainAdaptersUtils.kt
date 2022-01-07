package sibfu.tradeapp.screens.main.adapters

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.screens.main.KEY_ME

fun Fragment.setArgs(me: Employee, pair: Pair<String, Array<out Parcelable>>) =
    apply { arguments = bundleOf(KEY_ME to me, pair) }
