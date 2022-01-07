package sibfu.tradeapp.screens.common

import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.databinding.ItemClientBinding
import sibfu.tradeapp.db.entities.interfaces.HasName

class OneLineViewHolder(
    private val binding: ItemClientBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun <T : HasName> bind(element: T, onClick: (T) -> Unit) {
        with(binding) {
            textView.text = element.name
            root.setOnClickListener { onClick(element) }
        }
    }
}
