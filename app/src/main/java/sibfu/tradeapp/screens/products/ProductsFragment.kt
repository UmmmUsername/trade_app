package sibfu.tradeapp.screens.products

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentCommonRecyclerBinding
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.screens.common.OneLineAdapter
import sibfu.tradeapp.screens.main.MainFragmentDirections
import sibfu.tradeapp.utils.navigateUp

class ProductsFragment : Fragment(R.layout.fragment_common_recycler) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentCommonRecyclerBinding.bind(view).setupView()
    }

    private fun FragmentCommonRecyclerBinding.setupView() {
        val args: ProductsFragmentArgs by navArgs()
        val (flow, products) = args

        val onAdapterElementClick: (Product) -> Unit
        val isFloatingButtonVisible: Boolean
        val isToolbarVisible: Boolean

        when (flow) {
            is ProductsFlow.Adapter -> {
                val me = flow.me

                onAdapterElementClick = { product ->
                    onProductClick(product = product, myRole = me.role)
                }

                isFloatingButtonVisible = me.role == Role.WORKER && me.isActive
                isToolbarVisible = false
            }

            is ProductsFlow.Choosing -> {
                onAdapterElementClick = { product ->
                    setProductResult(isSale = flow.isSale, product = product)
                }

                isFloatingButtonVisible = false
                isToolbarVisible = true
            }
        }

        val adapter = OneLineAdapter(elements = products, onElementClick = onAdapterElementClick)

        appBarLayout.isVisible = isToolbarVisible
        toolbar.title = getString(R.string.select_product)
        toolbar.setNavigationOnClickListener { navigateUp() }
        floatingActionButton.isVisible = isFloatingButtonVisible
        floatingActionButton.setOnClickListener { TODO() }
        recyclerView.adapter = adapter
    }

    private fun onProductClick(product: Product, myRole: Role) {
        val direction = MainFragmentDirections.toProductFragment(myRole = myRole, product = product)
        findNavController().navigate(direction)
    }

    private fun setProductResult(isSale: Boolean, product: Product) {
        val key = if (isSale) PRODUCT_TO_SELL else PRODUCT_TO_BUY
        setFragmentResult(requestKey = key, result = bundleOf(PRODUCT to product))
        findNavController().navigateUp()
    }

    companion object {
        const val PRODUCT_TO_SELL = "PRODUCT_TO_SELL"
        const val PRODUCT_TO_BUY = "PRODUCT_TO_BUY"
        const val PRODUCT = "PRODUCT"
    }
}
