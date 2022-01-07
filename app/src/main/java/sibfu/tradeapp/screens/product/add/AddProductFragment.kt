package sibfu.tradeapp.screens.product.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentAddProductBinding

class AddProductFragment : Fragment(R.layout.fragment_add_product) {

    private val FragmentAddProductBinding.inputLayouts: Array<TextInputLayout>
        get() = arrayOf(nameTextInputLayout, unitTextInputLayout, priceTextInputLayout)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentAddProductBinding.bind(view).setupView()
    }

    private fun FragmentAddProductBinding.setupView() {
        toolbar.setNavigationOnClickListener { navigateUp() }
        button.setOnClickListener { onAddButtonClick() }

        priceEditText.setNumbersOnlyInputType()
        priceEditText.restrictByIntBounds()
        setRemoveErrorOnFocusListener(layouts = inputLayouts)
    }

    private fun FragmentAddProductBinding.onAddButtonClick() {
        if (!validateInput(layouts = inputLayouts)) return

        val name = nameEditText.inputText?.trim() ?: return
        val unit = unitEditText.inputText?.trim() ?: return
        val price = priceEditText.inputText?.toIntOrNull() ?: return

        val product = Product(name = name, unit = unit, price = price)

        val viewModel: AddProductViewModel by viewModels()
        viewModel.add(product = product) { navigateUp() }
    }
}
