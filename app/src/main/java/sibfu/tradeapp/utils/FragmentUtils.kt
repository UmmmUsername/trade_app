package sibfu.tradeapp.utils

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import sibfu.tradeapp.activity.MainActivity

val Fragment.mainActivity: MainActivity
    get() = requireActivity() as MainActivity

val Fragment.preferences: SharedPreferences
    get() = mainActivity.preferences
