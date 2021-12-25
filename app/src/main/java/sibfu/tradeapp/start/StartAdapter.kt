package sibfu.tradeapp.start

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class StartAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            LOGIN_POSITION -> LoginFragment()
            REGISTER_POSITION -> RegisterFragment()
            else -> throw IllegalArgumentException("Forbidden position: $position")
        }
    }

    companion object {
        const val LOGIN_POSITION = 0
        const val REGISTER_POSITION = 1
        private const val ITEM_COUNT = 2
    }
}
