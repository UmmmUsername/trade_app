package sibfu.tradeapp.screens.client.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentAddClientBinding
import sibfu.tradeapp.utils.inputText
import sibfu.tradeapp.utils.navigateUp
import sibfu.tradeapp.utils.setRemoveErrorOnFocusListener
import sibfu.tradeapp.utils.validateInput

class AddClientFragment : Fragment(R.layout.fragment_add_client) {

    private val FragmentAddClientBinding.inputLayouts: Array<TextInputLayout>
        get() = arrayOf(nameTextInputLayout, addressTextInputLayout)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FragmentAddClientBinding.bind(view).setupView()
    }

    private fun FragmentAddClientBinding.setupView() {
        val viewModel: AddClientViewModel by viewModels()

        toolbar.setNavigationOnClickListener { navigateUp() }
        setRemoveErrorOnFocusListener(layouts = inputLayouts)

        button.setOnClickListener { onSaveButtonClick(viewModel = viewModel) }
    }

    private fun FragmentAddClientBinding.onSaveButtonClick(viewModel: AddClientViewModel) {
        if (!validateInput(layouts = inputLayouts)) return

        val name = nameEditText.inputText?.trim() ?: return
        val address = addressEditText.inputText?.trim() ?: return

        viewModel.save(name = name, legalAddress = address, onSaved = { navigateUp() })
    }
}
