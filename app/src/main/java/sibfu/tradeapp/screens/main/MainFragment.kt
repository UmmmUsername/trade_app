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
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentMainBinding
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.adapters.AdminAdapter
import sibfu.tradeapp.utils.PreferenceKeys
import sibfu.tradeapp.utils.preferences
import sibfu.tradeapp.utils.throwIllegalPositionException

class MainFragment : Fragment(R.layout.fragment_main) {

    private val args: MainFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val myRole: Role
        get() = args.employee.role

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMainBinding.bind(view)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestData() {
        when (myRole) {
            Role.ADMIN -> viewModel.requestAdminData()

            Role.DISPATCHER -> TODO()
            Role.WORKER -> TODO()
        }
    }

    private fun setupScreen(data: MainScreenData) {
        val (adapter, strategy) = when (data) {
            is AdminData -> handleAdminFlow(data = data)
        }

        with(binding) {
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager, strategy).attach()
        }
    }

    private fun handleAdminFlow(
        data: AdminData,
    ): Pair<FragmentStateAdapter, TabLayoutMediator.TabConfigurationStrategy> {
        val (employees, clients, deals) = data

        val adapter = AdminAdapter(
            fragment = this,
            employees = employees,
            clients = clients,
            deals = deals,
        )

        val strategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val tabTitleRes = when (position) {
                AdminAdapter.EMPLOYEES_POSITION -> R.string.employees
                AdminAdapter.CLIENTS_POSITION -> R.string.clients
                AdminAdapter.DEALS_POSITION -> R.string.deals
                else -> throwIllegalPositionException(position = position)
            }

            tab.text = getString(tabTitleRes)
        }

        return adapter to strategy
    }
}
