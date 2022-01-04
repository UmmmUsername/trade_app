package sibfu.tradeapp.screens.clients

import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.databinding.ItemClientBinding
import sibfu.tradeapp.db.entities.Client

class ClientViewHolder(
    private val binding: ItemClientBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(client: Client, onClick: (Client) -> Unit) {
        with(binding) {
            textView.text = client.name
            root.setOnClickListener { onClick(client) }
        }
    }
}
