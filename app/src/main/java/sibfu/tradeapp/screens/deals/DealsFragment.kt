package sibfu.tradeapp.screens.deals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentDealsBinding
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.MY_ROLE_KEY
import sibfu.tradeapp.screens.main.MainFragmentDirections
import kotlin.math.abs

class DealsFragment : Fragment(R.layout.fragment_deals) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fullDeals = arguments?.getParcelableArray(DEALS) as? Array<FullDeal> ?: return
        val myRole = arguments?.getSerializable(MY_ROLE_KEY) as? Role ?: return

        val adapter = DealsAdapter(fullDeals = fullDeals) { fullDeal ->
            onDealClicked(fullDeal = fullDeal, myRole = myRole)
        }

        val income = fullDeals.calculateFullPrice { fullDeal -> fullDeal.deal.amount >= 0 } ?: 0
        val costs = abs(fullDeals.calculateFullPrice { fullDeal -> fullDeal.deal.amount < 0 } ?: 0)

        with(FragmentDealsBinding.bind(view)) {
            recyclerView.adapter = adapter
            incomeTextView.text = getString(R.string.income_pattern, income)
            costsTextView.text = getString(R.string.costs_pattern, costs)
        }
    }

    private fun onDealClicked(fullDeal: FullDeal, myRole: Role) {
        val direction = MainFragmentDirections.toDealFragment(fullDeal = fullDeal, myRole = myRole)
        findNavController().navigate(direction)
    }

    private fun Array<FullDeal>.calculateFullPrice(predicate: (FullDeal) -> Boolean): Int? {
        return filter { fullDeal -> predicate(fullDeal) }
            .map { fullDeal -> fullDeal.deal.amount * fullDeal.product.price }
            .reduceOrNull { first, second -> first + second }
    }

    companion object {
        const val DEALS = "deals"
    }
}
