package sibfu.tradeapp.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.databinding.ItemClientBinding
import sibfu.tradeapp.db.entities.interfaces.HasName

class OneLineAdapter<T : HasName>(
    private val elements: Array<T>,
    private val onElementClick: (T) -> Unit,
) : RecyclerView.Adapter<OneLineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemClientBinding.inflate(inflater, parent, false)
        return OneLineViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: OneLineViewHolder, position: Int) {
        holder.bind(element = elements[position], onClick = onElementClick)
    }

    override fun getItemCount(): Int =
        elements.size
}

