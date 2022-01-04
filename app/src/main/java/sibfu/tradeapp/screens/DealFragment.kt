package sibfu.tradeapp.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentDealBinding
import sibfu.tradeapp.db.entities.Client
import sibfu.tradeapp.db.entities.Product
import sibfu.tradeapp.models.Role
import sibfu.tradeapp.utils.navigateUp
import kotlin.math.abs

class DealFragment : Fragment(R.layout.fragment_deal) {

    private val args: DealFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val (myRole, fullDeal) = args
        val (deal, employee, client, product) = fullDeal
        val (id, _, _, _, amount) = deal

        val employeeName = employee.getShortenedName(context = requireContext())
        val clientName = client.name
        val onEmployeeClicked = { moveToEmployee(myRole = myRole, employeeId = employee.id) }
        val onClientClicked = { moveToClient(myRole = myRole, client = client) }

        val dealTypeRes: Int
        val sellerName: String
        val buyerName: String
        val onSellerClicked: () -> Unit
        val onBuyerClicked: () -> Unit

        if (amount < 0) {
            dealTypeRes = R.string.deal_type_purchase
            sellerName = clientName
            buyerName = employeeName
            onSellerClicked = onClientClicked
            onBuyerClicked = onEmployeeClicked
        } else {
            dealTypeRes = R.string.deal_type_sale
            sellerName = employeeName
            buyerName = clientName
            onSellerClicked = onEmployeeClicked
            onBuyerClicked = onClientClicked
        }

        val sumString = getString(R.string.ruble_pattern, abs(amount) * product.price)

        val amountString =
            getString(R.string.space_separated_pattern, abs(deal.amount).toString(), product.unit)

        with(FragmentDealBinding.bind(view)) {
            toolbar.setNavigationOnClickListener { navigateUp() }

            numberView.descriptionTextView.text = id.toString()
            dealTypeView.descriptionTextView.text = getString(dealTypeRes)
            sumView.descriptionTextView.text = sumString
            sellerView.descriptionTextView.text = sellerName
            buyerView.descriptionTextView.text = buyerName
            productView.descriptionTextView.text = product.name
            amountView.descriptionTextView.text = amountString

            sellerView.setOnClickListener { onSellerClicked() }
            buyerView.setOnClickListener { onBuyerClicked() }
            productView.setOnClickListener { moveToProduct(myRole = myRole, product = product) }
        }
    }

    private fun moveToProduct(myRole: Role, product: Product) {
        val direction = DealFragmentDirections.toProductFragment(myRole = myRole, product = product)
        findNavController().navigate(direction)
    }

    private fun moveToEmployee(myRole: Role, employeeId: Int) {
        val direction = DealFragmentDirections.toEmployee(myRole = myRole, employeeId = employeeId)
        findNavController().navigate(direction)
    }

    private fun moveToClient(myRole: Role, client: Client) {
        val direction = DealFragmentDirections.toClientFragment(myRole = myRole, client = client)
        findNavController().navigate(direction)
    }
}
