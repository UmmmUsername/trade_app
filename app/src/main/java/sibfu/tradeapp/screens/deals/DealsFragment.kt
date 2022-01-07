package sibfu.tradeapp.screens.deals

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentDealsBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.FullDeal
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.KEY_ME
import sibfu.tradeapp.screens.main.MainFragmentDirections
import kotlin.math.abs

class DealsFragment : Fragment(R.layout.fragment_deals) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fullDeals = arguments?.getParcelableArray(DEALS) as? Array<FullDeal> ?: return
        val me = arguments?.getParcelable<Employee>(KEY_ME) ?: return

        val adapter = DealsAdapter(fullDeals = fullDeals) { fullDeal ->
            onDealClicked(fullDeal = fullDeal, myRole = me.role)
        }

        val income = fullDeals.calculateFullPrice { fullDeal -> fullDeal.deal.amount >= 0 } ?: 0
        val costs = abs(fullDeals.calculateFullPrice { fullDeal -> fullDeal.deal.amount < 0 } ?: 0)

        with(FragmentDealsBinding.bind(view)) {
            recyclerView.adapter = adapter
            incomeTextView.text = getString(R.string.income_pattern, income)
            costsTextView.text = getString(R.string.costs_pattern, costs)

            floatingActionButton.isVisible = me.role == Role.WORKER && me.isActive
            floatingActionButton.setOnClickListener { moveToDealAdding(me = me) }
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

    private fun moveToDealAdding(me: Employee) {
        val direction = MainFragmentDirections.toDealAdding(me = me)
        findNavController().navigate(direction)
    }

    companion object {
        const val DEALS = "deals"
    }
}
