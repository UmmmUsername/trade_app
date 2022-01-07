package sibfu.tradeapp.screens.deal.add

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.screens.deal.trade.TradeData
import sibfu.tradeapp.screens.deal.trade.TradeFragment
import sibfu.tradeapp.utils.throwIllegalPositionException

class AddDealAdapter(
    fragment: Fragment,
    private val me: Employee,
    private val clients: Array<Client>,
    private val products: Array<Product>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int =
        ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        val isSale = when (position) {
            SELL_POSITION -> true
            BUY_POSITION -> false
            else -> throwIllegalPositionException(position = position)
        }

        val data = TradeData(isSale = isSale, me = me, clients = clients, products = products)
        return TradeFragment().apply { arguments = bundleOf(TradeFragment.DATA to data) }
    }

    companion object {
        const val SELL_POSITION = 0
        const val BUY_POSITION = 1

        private const val ITEM_COUNT = 2
    }
}
