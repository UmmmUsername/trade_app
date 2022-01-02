package sibfu.tradeapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.textview.MaterialTextView
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.ViewTwoLinesItemBinding

class TwoLinesItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val binding = ViewTwoLinesItemBinding.inflate(LayoutInflater.from(context), this, true)

    val titleTextView: MaterialTextView
        get() = binding.titleTextView

    val descriptionTextView: MaterialTextView
        get() = binding.descriptionTextView

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoLinesItemView)

        try {
            titleTextView.text = typedArray.getText(R.styleable.TwoLinesItemView_title) ?: ""
            descriptionTextView.text =
                typedArray.getText(R.styleable.TwoLinesItemView_description) ?: ""
        } finally {
            typedArray.recycle()
        }

        isClickable = true
        isFocusable = true
    }
}
