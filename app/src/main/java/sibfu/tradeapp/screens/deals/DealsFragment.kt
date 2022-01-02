package sibfu.tradeapp.screens.deals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentLinearVerticalRecyclerViewBinding
import sibfu.tradeapp.db.entities.FullDeal

class DealsFragment : Fragment(R.layout.fragment_linear_vertical_recycler_view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fullDeals = arguments?.getParcelableArray(DEALS) as? Array<FullDeal> ?: return

        with(FragmentLinearVerticalRecyclerViewBinding.bind(view)) {
            recyclerView.adapter = DealsAdapter(fullDeals = fullDeals)
        }
    }

    companion object {
        const val DEALS = "deals"
    }
}
