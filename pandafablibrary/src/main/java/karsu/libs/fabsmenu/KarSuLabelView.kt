package karsu.libs.fabsmenu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

class KarSuLabelView : CardView {

    private var labelBackgroundColor: Int = Color.WHITE

    constructor(context: Context) : super(context) {
        applyBackgroundColor()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyBackgroundColor()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyBackgroundColor()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        backgroundColor: Int
    ) : super(context, attrs, defStyleAttr) {
        labelBackgroundColor = backgroundColor
        applyBackgroundColor()
    }

    var content: TextView? = null

    var textColor: Int = 0
        set(value) {
            field = value
            content?.setTextColor(value)
        }

    var textColorRes: Int = 0
        set(value) {
            field = value
            textColor = ContextCompat.getColor(context, value)
        }

    private fun applyBackgroundColor() {
        super.setCardBackgroundColor(labelBackgroundColor)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        l?.let {
            TypedValue().also { outValue ->
                context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                foreground = ContextCompat.getDrawable(context, outValue.resourceId)
            }
            isClickable = true
            isFocusable = true
        } ?: run {
            foreground = null
            isClickable = false
            isFocusable = false
        }
        super.setOnClickListener(l)
    }

    override fun setBackgroundColor(backgroundColor: Int) {
        labelBackgroundColor = backgroundColor
        applyBackgroundColor()
    }

    override fun setCardBackgroundColor(color: Int) {
        labelBackgroundColor = color
        applyBackgroundColor()
    }
}
