package sibfu.tradeapp.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.FragmentStartBinding
import sibfu.tradeapp.start.StartAdapter.Companion.LOGIN_POSITION
import sibfu.tradeapp.start.StartAdapter.Companion.REGISTER_POSITION

class StartFragment : Fragment(R.layout.fragment_start) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentStartBinding.bind(view)) {
            viewPager.adapter = StartAdapter(fragment = this@StartFragment)

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    LOGIN_POSITION -> tab.text = getString(R.string.login)
                    REGISTER_POSITION -> tab.text = getString(R.string.register)
                }
            }.attach()
        }
    }
}
