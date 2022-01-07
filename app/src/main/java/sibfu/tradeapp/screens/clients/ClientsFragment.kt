package sibfu.tradeapp.screens.clients

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentCommonRecyclerBinding
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.common.OneLineAdapter
import sibfu.tradeapp.screens.main.MainFragmentDirections
import sibfu.tradeapp.utils.navigateUp

class ClientsFragment : Fragment(R.layout.fragment_common_recycler) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: ClientsFragmentArgs by navArgs()
        val (flow, clients) = args

        val onAdapterElementClick: (Client) -> Unit
        val isFloatingButtonVisible: Boolean
        val isToolbarVisible: Boolean

        when (flow) {
            is ClientsFlow.Adapter -> {
                val me = flow.me

                onAdapterElementClick = { client ->
                    moveToClient(client = client, myRole = me.role)
                }

                isFloatingButtonVisible = me.role == Role.WORKER && me.isActive
                isToolbarVisible = false
            }

            is ClientsFlow.Choosing -> {
                onAdapterElementClick = { client ->
                    setClientResult(isSale = flow.isSale, client = client)
                }

                isFloatingButtonVisible = false
                isToolbarVisible = true
            }
        }

        val adapter = OneLineAdapter(elements = clients, onElementClick = onAdapterElementClick)

        with(FragmentCommonRecyclerBinding.bind(view)) {
            toolbar.title = getString(R.string.select_client)
            toolbar.setNavigationOnClickListener { navigateUp() }
            appBarLayout.isVisible = isToolbarVisible
            recyclerView.adapter = adapter
            floatingActionButton.isVisible = isFloatingButtonVisible
            floatingActionButton.setOnClickListener { moveToClientAdding() }
        }
    }

    private fun moveToClient(client: Client, myRole: Role) {
        val direction = MainFragmentDirections.toClientFragment(client = client, myRole = myRole)
        findNavController().navigate(direction)
    }

    private fun setClientResult(isSale: Boolean, client: Client) {
        val key = if (isSale) CLIENT_BUYER else CLIENT_SELLER
        setFragmentResult(requestKey = key, result = bundleOf(CLIENT to client))
        findNavController().navigateUp()
    }

    private fun moveToClientAdding() {
        val direction = MainFragmentDirections.toClientAdding()
        findNavController().navigate(direction)
    }

    companion object {
        const val CLIENT_BUYER = "CLIENT_BUYER"
        const val CLIENT_SELLER = "CLIENT_SELLER"
        const val CLIENT = "CLIENT"
    }
}
