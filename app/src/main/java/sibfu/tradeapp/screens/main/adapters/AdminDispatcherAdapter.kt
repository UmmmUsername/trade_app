package sibfu.tradeapp.screens.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.screens.clients.ClientsFlow
import sibfu.tradeapp.screens.clients.ClientsFragment
import sibfu.tradeapp.screens.clients.ClientsFragmentArgs
import sibfu.tradeapp.screens.deals.DealsFragment
import sibfu.tradeapp.screens.employees.EmployeesFragment
import sibfu.tradeapp.utils.throwIllegalPositionException

class AdminDispatcherAdapter(
    fragment: Fragment,
    private val me: Employee,
    private val employees: Array<Employee>,
    private val clients: Array<Client>,
    private val fullDeals: Array<FullDeal>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            EMPLOYEES_POSITION -> {
                val employeesPair = EmployeesFragment.EMPLOYEES to employees
                EmployeesFragment().setArgs(me = me, pair = employeesPair)
            }

            CLIENTS_POSITION -> ClientsFragment().apply {
                val flow = ClientsFlow.Adapter(me = me)
                arguments = ClientsFragmentArgs(flow = flow, clients = clients).toBundle()
            }

            DEALS_POSITION ->
                DealsFragment().setArgs(me = me, pair = DealsFragment.DEALS to fullDeals)

            else -> throwIllegalPositionException(position = position)
        }
    }

    companion object {
        const val EMPLOYEES_POSITION = 0
        const val CLIENTS_POSITION = 1
        const val DEALS_POSITION = 2

        private const val ITEM_COUNT = 3
    }
}
