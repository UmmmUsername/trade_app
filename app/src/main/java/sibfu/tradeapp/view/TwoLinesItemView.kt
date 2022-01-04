package sibfu.tradeapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.textview.MaterialTextView
import sibfu.tradeapp.R
import sibfu.tradeapp.databinding.ViewTwoLinesItemBinding

class TwoLinesItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewTwoLinesItemBinding.inflate(LayoutInflater.from(context), this, true)

    val titleTextView: MaterialTextView
        get() = binding.titleTextView

    val descriptionTextView: MaterialTextView
        get() = binding.descriptionTextView

    val arrowImageView: ImageView
        get() = binding.arrowImageView

    init {
        val set = intArrayOf(android.R.attr.clickable)
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoLinesItemView)
        val androidTypedArray = context.obtainStyledAttributes(attrs, set)

        try {
            titleTextView.text = typedArray.getText(R.styleable.TwoLinesItemView_title)
            descriptionTextView.text =
                typedArray.getText(R.styleable.TwoLinesItemView_description)

            val clickable = androidTypedArray.getBoolean(0, false)

            arrowImageView.isVisible = clickable
            isClickable = clickable
            isFocusable = clickable
        } finally {
            typedArray.recycle()
            androidTypedArray.recycle()
        }
    }
}
