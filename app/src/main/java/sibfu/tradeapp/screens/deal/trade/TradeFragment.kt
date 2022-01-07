package sibfu.tradeapp.screens.deal.trade

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentSellBuyBinding
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.screens.clients.ClientsFlow
import sibfu.tradeapp.screens.clients.ClientsFragment
import sibfu.tradeapp.screens.deal.add.AddDealFragmentDirections
import sibfu.tradeapp.screens.products.ProductsFlow
import sibfu.tradeapp.screens.products.ProductsFragment
import sibfu.tradeapp.utils.restrictByIntBounds
import sibfu.tradeapp.utils.setNumbersOnlyInputType

class TradeFragment : Fragment(R.layout.fragment_sell_buy) {

    private val viewModel: TradeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentSellBuyBinding.bind(view).observeViewModel()
    }

    private fun FragmentSellBuyBinding.observeViewModel() {
        val data = arguments?.getParcelable<TradeData>(DATA) ?: return
        val (isSale, me, _, _) = data

        val buttonTextRes = if (isSale) R.string.sale else R.string.purchase
        button.text = getString(buttonTextRes)

        button.setOnClickListener {
            viewModel.save(isSale = isSale, employee = me) { findNavController().navigateUp() }
        }

        amountEditText.setNumbersOnlyInputType()
        amountEditText.restrictByIntBounds()

        amountEditText.doAfterTextChanged { editable ->
            viewModel.updateAmount(amount = editable?.toString()?.toIntOrNull())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    handleViewModelState(data = data, state = state)
                }
            }
        }
    }

    private fun FragmentSellBuyBinding.handleViewModelState(data: TradeData, state: TradeState) {
        val (isSale, me, clients, products) = data
        val (client, product, amount, errorRes) = state

        registerFragmentListeners(isSale = isSale)

        val clientItemView = if (isSale) buyerView else sellerView
        val employeeItemView = if (isSale) sellerView else buyerView

        with(employeeItemView) {
            isClickable = false
            descriptionTextView.text = me.getShortenedName(context = requireContext())
        }

        if (client == null) {
            with(clientItemView) {
                isClickable = true
                descriptionTextView.text = getString(R.string.not_chosen)
                setOnClickListener { moveToClients(isSale = isSale, clients = clients) }
            }
        } else {
            clientItemView.descriptionTextView.text = client.name
        }

        val productName: String
        val productUnit: String

        if (product == null) {
            productName = getString(R.string.not_chosen)
            productUnit = getString(R.string.unit_single)
        } else {
            productName = product.name
            productUnit = product.unit
        }

        productView.descriptionTextView.text = productName
        unitTextView.text = productUnit

        productView.setOnClickListener { moveToProducts(isSale = isSale, products = products) }

        when {
            product == null -> sumTextView.isVisible = false

            amount == null -> {
                sumTextView.isVisible = true
                sumTextView.text = getString(R.string.invalid_amount)
            }

            else -> {
                sumTextView.isVisible = true
                sumTextView.text = getString(R.string.sum_pattern, amount * product.price)
            }
        }

        errorRes?.let { res ->
            showToast(res = res)
            viewModel.onErrorConsumed()
        }
    }

    private fun moveToClients(isSale: Boolean, clients: Array<Client>) {
        val direction = AddDealFragmentDirections.toClientsFragment(
            flow = ClientsFlow.Choosing(isSale = isSale),
            clients = clients,
        )

        findNavController().navigate(direction)
    }

    private fun moveToProducts(isSale: Boolean, products: Array<Product>) {
        val direction = AddDealFragmentDirections.toProductsFragment(
            flow = ProductsFlow.Choosing(isSale = isSale),
            products = products,
        )

        findNavController().navigate(direction)
    }

    private fun showToast(res: Int) {
        Toast.makeText(requireContext(), res, Toast.LENGTH_SHORT).show()
    }

    private fun registerFragmentListeners(isSale: Boolean) {
        val clientKey: String
        val productKey: String

        if (isSale) {
            clientKey = ClientsFragment.CLIENT_BUYER
            productKey = ProductsFragment.PRODUCT_TO_SELL
        } else {
            clientKey = ClientsFragment.CLIENT_SELLER
            productKey = ProductsFragment.PRODUCT_TO_BUY
        }

        parentFragment?.setFragmentResultListener(
            requestKey = clientKey,
            listener = { _, bundle -> listenToChosenClient(bundle = bundle) },
        )

        parentFragment?.setFragmentResultListener(
            requestKey = productKey,
            listener = { _, bundle -> listenToChosenProduct(bundle = bundle) },
        )
    }

    private fun listenToChosenClient(bundle: Bundle) {
        val client: Client = bundle.getParcelable(ClientsFragment.CLIENT) ?: return
        viewModel.selectClient(client = client)
    }

    private fun listenToChosenProduct(bundle: Bundle) {
        val product: Product = bundle.getParcelable(ProductsFragment.PRODUCT) ?: return
        viewModel.selectProduct(product = product)
    }

    companion object {
        const val DATA = "data"
    }
}
