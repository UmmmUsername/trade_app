package sibfu.tradeapp.screens.deals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.databinding.ItemDealBinding
import sibfu.tradeapp.db.entities.FullDeal

class DealsAdapter(
    private val fullDeals: Array<FullDeal>,
) : RecyclerView.Adapter<DealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDealBinding.inflate(inflater, parent, false)
        return DealViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        holder.bind(fullDeal = fullDeals[position])
    }

    override fun getItemCount(): Int =
        fullDeals.size
}
