package sibfu.tradeapp.screens.deal.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentAddDealBinding
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.utils.navigateUp
import sibfu.tradeapp.utils.throwIllegalPositionException

class AddDealFragment : Fragment(R.layout.fragment_add_deal) {

    private val args: AddDealFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentAddDealBinding.bind(view)
        val viewModel: AddDealViewModel by viewModels()

        with(binding) {
            toolbar.setNavigationOnClickListener { navigateUp() }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state ->
                        state.clientsToProducts?.let { clientsToProducts ->
                            val (clients, products) = clientsToProducts
                            val (adapter, strategy) = getAdapterAndStrategy(
                                me = args.me,
                                clients = clients,
                                products = products,
                            )

                            viewPager.adapter = adapter
                            TabLayoutMediator(tabLayout, viewPager, strategy).attach()
                        }
                    }
                }
            }
        }
    }

    private fun getAdapterAndStrategy(
        me: Employee,
        clients: Array<Client>,
        products: Array<Product>,
    ): Pair<FragmentStateAdapter, TabLayoutMediator.TabConfigurationStrategy> {
        val adapter =
            AddDealAdapter(fragment = this, me = me, clients = clients, products = products)
        val strategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val tabTitleRes = when (position) {
                AddDealAdapter.SELL_POSITION -> R.string.sell
                AddDealAdapter.BUY_POSITION -> R.string.buy
                else -> throwIllegalPositionException(position = position)
            }

            tab.text = getString(tabTitleRes)
        }

        return adapter to strategy
    }
}
