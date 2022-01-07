package sibfu.tradeapp.screens.main.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.screens.clients.ClientsFlow
import sibfu.tradeapp.screens.clients.ClientsFragment
import sibfu.tradeapp.screens.clients.ClientsFragmentArgs
import sibfu.tradeapp.screens.deals.DealsFragment
import sibfu.tradeapp.screens.products.ProductsFlow
import sibfu.tradeapp.screens.products.ProductsFragment
import sibfu.tradeapp.screens.products.ProductsFragmentArgs
import sibfu.tradeapp.utils.throwIllegalPositionException

class WorkerAdapter(
    fragment: Fragment,
    private val me: Employee,
    private val fullDeals: Array<FullDeal>,
    private val clients: Array<Client>,
    private val products: Array<Product>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            DEALS_POSITION ->
                DealsFragment().setArgs(me = me, pair = DealsFragment.DEALS to fullDeals)

            CLIENTS_POSITION -> ClientsFragment().apply {
                val flow = ClientsFlow.Adapter(me = me)
                arguments = ClientsFragmentArgs(flow = flow, clients = clients).toBundle()
            }

            PRODUCT_POSITION -> ProductsFragment().apply {
                val flow = ProductsFlow.Adapter(me = me)
                arguments = ProductsFragmentArgs(flow = flow, products = products).toBundle()
            }

            else -> throwIllegalPositionException(position = position)
        }
    }

    companion object {
        const val DEALS_POSITION = 0
        const val CLIENTS_POSITION = 1
        const val PRODUCT_POSITION = 2

        private const val ITEM_COUNT = 3
    }
}
