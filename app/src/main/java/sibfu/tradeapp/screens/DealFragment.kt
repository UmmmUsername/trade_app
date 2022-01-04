package sibfu.tradeapp.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentDealBinding
import sibfu.tradeapp.utils.navigateUp
import kotlin.math.abs

class DealFragment : Fragment(R.layout.fragment_deal) {

    private val args: DealFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val (deal, employee, client, product) = args.fullDeal
        val (id, _, _, _, amount) = deal

        val employeeName = employee.getShortenedName(context = requireContext())
        val clientName = client.name
        val navController = findNavController()

        val onEmployeeClicked = {
            val direction = DealFragmentDirections.toEmployee(employeeId = employee.id)
            navController.navigate(direction)
        }

        val onClientClicked = {
            val direction = DealFragmentDirections.toClientFragment(client = client)
            navController.navigate(direction)
        }

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
        }
    }
}
