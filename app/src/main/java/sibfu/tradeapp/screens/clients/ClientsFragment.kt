package sibfu.tradeapp.screens.clients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLinearVerticalRecyclerViewBinding
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.main.MY_ROLE_KEY
import sibfu.tradeapp.screens.main.MainFragmentDirections

class ClientsFragment : Fragment(R.layout.fragment_linear_vertical_recycler_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val clients = arguments?.getParcelableArray(CLIENTS) as? Array<Client> ?: return
        val myRole = arguments?.getSerializable(MY_ROLE_KEY) as? Role ?: return

        val adapter = ClientsAdapter(clients = clients) { client ->
            onClientClicked(client = client, myRole = myRole)
        }

        with(FragmentLinearVerticalRecyclerViewBinding.bind(view)) {
            recyclerView.adapter = adapter
        }
    }

    private fun onClientClicked(client: Client, myRole: Role) {
        val direction = MainFragmentDirections.toClientFragment(client = client, myRole = myRole)
        findNavController().navigate(direction)
    }

    companion object {
        const val CLIENTS = "clients"
    }
}
