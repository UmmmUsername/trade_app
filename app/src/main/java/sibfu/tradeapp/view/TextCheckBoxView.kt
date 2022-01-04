package sibfu.tradeapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView
import sibfu.tradeapp.databinding.ViewTextCheckBoxBinding

class TextCheckBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val binding = ViewTextCheckBoxBinding.inflate(LayoutInflater.from(context), this, true)

    val textView: MaterialTextView
        get() = binding.textView

    val checkBox: MaterialCheckBox
        get() = binding.checkBox

    init {
        val set = intArrayOf(android.R.attr.text)
        val typedArray = context.obtainStyledAttributes(attrs, set)

        try {
            textView.text = typedArray.getText(0)
        } finally {
            typedArray.recycle()
        }
    }
}
