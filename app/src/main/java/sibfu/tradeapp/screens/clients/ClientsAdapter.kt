package sibfu.tradeapp.screens.clients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.databinding.ItemClientBinding
import sibfu.tradeapp.db.entities.Client

class ClientsAdapter(
    private val clients: Array<Client>,
    private val onClientClick: (Client) -> Unit,
) : RecyclerView.Adapter<ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClientBinding.inflate(inflater, parent, false)
        return ClientViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bind(client = clients[position], onClick = onClientClick)
    }

    override fun getItemCount(): Int =
        clients.size
}
