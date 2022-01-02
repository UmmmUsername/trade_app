package sibfu.tradeapp.screens.clients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLinearVerticalRecyclerViewBinding
import sibfu.tradeapp.db.entities.Client

class ClientsFragment : Fragment(R.layout.fragment_linear_vertical_recycler_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val clients = arguments?.getParcelableArray(CLIENTS) as? Array<Client> ?: return

        with(FragmentLinearVerticalRecyclerViewBinding.bind(view)) {
            recyclerView.adapter = ClientsAdapter(clients = clients)
        }
    }

    companion object {
        const val CLIENTS = "clients"
    }
}
