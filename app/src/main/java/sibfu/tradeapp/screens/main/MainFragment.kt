package sibfu.tradeapp.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentMainBinding
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.adapters.AdminDispatcherAdapter
import sibfu.tradeapp.screens.main.adapters.WorkerAdapter
import sibfu.tradeapp.utils.PreferenceKeys
import sibfu.tradeapp.utils.preferences
import sibfu.tradeapp.utils.throwIllegalPositionException

class MainFragment : Fragment(R.layout.fragment_main) {

    private val args: MainFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val me: Employee
        get() = args.employee

    private val myRole: Role
        get() = me.role

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestData()

        val titleRes = when (myRole) {
            Role.ADMIN -> R.string.admin
            Role.DISPATCHER -> R.string.dispatcher
            Role.WORKER -> R.string.worker
        }

        with(binding) {
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.actionLogout -> {
                        preferences.edit().remove(PreferenceKeys.CURRENT_EMPLOYEE).apply()
                        findNavController().navigate(MainFragmentDirections.toLogin())
                        true
                    }

                    else -> false
                }
            }

            toolbar.title = getString(titleRes)

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state ->
                        if (state.isLoading) {
                            // TODO set progress visibility to VISIBLE
                        } else {
                            // TODO set progress visibility to GONE
                        }

                        state.data?.let { data -> setupScreen(data = data) }
                    }
                }
            }
        }
    }

    private fun requestData() {
        when (myRole) {
            Role.ADMIN -> viewModel.requestAdminData()
            Role.DISPATCHER -> viewModel.requestDispatcherData()
            Role.WORKER -> viewModel.requestWorkerData()
        }
    }

    private fun setupScreen(data: MainScreenData) {
        val (adapter, strategy) = when (data) {
            is AdminDispatcherData -> handleAdminDispatcherFlow(data = data)
            is WorkerData -> handleWorkerFlow(data = data)
        }

        with(binding) {
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager, strategy).attach()
        }
    }

    private fun handleAdminDispatcherFlow(
        data: AdminDispatcherData,
    ): Pair<FragmentStateAdapter, TabLayoutMediator.TabConfigurationStrategy> {
        val (employees, clients, deals) = data

        val adapter = AdminDispatcherAdapter(
            fragment = this,
            me = me,
            employees = employees,
            clients = clients,
            fullDeals = deals,
        )

        val strategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val tabTitleRes = when (position) {
                AdminDispatcherAdapter.EMPLOYEES_POSITION -> R.string.employees
                AdminDispatcherAdapter.CLIENTS_POSITION -> R.string.clients
                AdminDispatcherAdapter.DEALS_POSITION -> R.string.deals
                else -> throwIllegalPositionException(position = position)
            }

            tab.text = getString(tabTitleRes)
        }

        return adapter to strategy
    }

    private fun handleWorkerFlow(
        data: WorkerData,
    ): Pair<FragmentStateAdapter, TabLayoutMediator.TabConfigurationStrategy> {
        val (deals, clients, products) = data

        val adapter = WorkerAdapter(
            fragment = this,
            me = me,
            fullDeals = deals,
            clients = clients,
            products = products,
        )

        val strategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val tabTitleRes = when (position) {
                WorkerAdapter.DEALS_POSITION -> R.string.deals
                WorkerAdapter.CLIENTS_POSITION -> R.string.clients
                WorkerAdapter.PRODUCT_POSITION -> R.string.products
                else -> throwIllegalPositionException(position = position)
            }

            tab.text = getString(tabTitleRes)
        }

        return adapter to strategy
    }
}
