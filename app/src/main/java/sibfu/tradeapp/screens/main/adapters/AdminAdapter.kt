package sibfu.tradeapp.screens.main.adapters

import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Deal
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.screens.ClientsFragment
import sibfu.tradeapp.screens.DealsFragment
import sibfu.tradeapp.screens.EmployeesFragment
import sibfu.tradeapp.utils.throwIllegalPositionException

class AdminAdapter(
    fragment: Fragment,
    private val employees: Array<Employee>,
    private val clients: Array<Client>,
    private val deals: Array<Deal>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            EMPLOYEES_POSITION ->
                EmployeesFragment().setArgs(EmployeesFragment.EMPLOYEES to employees)

            CLIENTS_POSITION ->
                ClientsFragment().setArgs(ClientsFragment.CLIENTS to clients)

            DEALS_POSITION ->
                DealsFragment().setArgs(DealsFragment.DEALS to deals)

            else -> throwIllegalPositionException(position = position)
        }
    }

    private fun Fragment.setArgs(pair: Pair<String, Array<out Parcelable>>) =
        apply { arguments = bundleOf(pair) }

    companion object {
        const val EMPLOYEES_POSITION = 0
        const val CLIENTS_POSITION = 1
        const val DEALS_POSITION = 2

        private const val ITEM_COUNT = 3
    }
}