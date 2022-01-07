package sibfu.tradeapp.screens.client

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentClientBinding
import sibfu.tradeapp.utils.navigateUp

class ClientFragment : Fragment(R.layout.fragment_client) {

    private val args: ClientFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val client = args.client

        with(FragmentClientBinding.bind(view)) {
            toolbar.setNavigationOnClickListener { navigateUp() }

            numberView.descriptionTextView.text = client.id.toString()
            nameView.descriptionTextView.text = client.name
            legalAddressView.descriptionTextView.text = client.legalAddress
        }
    }
}
