package sibfu.tradeapp.screens.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentProductBinding
import sibfu.tradeapp.utils.navigateUp

class ProductFragment : Fragment(R.layout.fragment_product) {

    private val args: ProductFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val (id, name, unit, price) = args.product

        with(FragmentProductBinding.bind(view)) {
            toolbar.setNavigationOnClickListener { navigateUp() }

            numberView.descriptionTextView.text = id.toString()
            nameView.descriptionTextView.text = name
            unitView.descriptionTextView.text = unit
            priceView.descriptionTextView.text = getString(R.string.ruble_pattern, price)
        }
    }
}
