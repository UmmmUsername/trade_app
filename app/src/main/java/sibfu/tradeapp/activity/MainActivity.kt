package sibfu.tradeapp.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sibfu.tradeapp.R

class MainActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getPreferences(Context.MODE_PRIVATE)
    }
}
