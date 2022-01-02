package sibfu.tradeapp.screens.loading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sibfu.tradeapp.R
import sibfu.tradeapp.db.entities.Employee
import sibfu.tradeapp.utils.preferences

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel: LoadingViewModel by viewModels()
        viewModel.requestEmployee(preferences = preferences)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (!state.isLoading) {
                        moveToNext(employee = state.employee)
                    }
                }
            }
        }
    }

    private fun moveToNext(employee: Employee?) {
        val direction =
            if (employee == null) {
                LoadingFragmentDirections.toLogin()
            } else {
                LoadingFragmentDirections.toMainFragment(employee = employee)
            }

        findNavController().navigate(direction)
    }
}
