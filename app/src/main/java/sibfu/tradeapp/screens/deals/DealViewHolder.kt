package sibfu.tradeapp.screens.deals

import androidx.recyclerview.widget.RecyclerView
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.ItemDealBinding
import sibfu.tradeapp.db.entities.FullDeal
import kotlin.math.abs

class DealViewHolder(
    private val binding: ItemDealBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(fullDeal: FullDeal) {
        with(binding) {
            val (deal, _, _, product) = fullDeal
            val amount = deal.amount
            val finalAmount = abs(n = amount)
            val descriptionRes = if (amount < 0) R.string.bought_pattern else R.string.sold_pattern

            with(root.context) {
                numberTextView.text = getString(R.string.deal_number_pattern, deal.id)
                descriptionTextView.text = getString(
                    descriptionRes,
                    product.name,
                    finalAmount,
                    product.unit,
                )
            }
        }
    }
}
