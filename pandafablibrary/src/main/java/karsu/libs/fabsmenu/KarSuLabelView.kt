package karsu.libs.fabsmenu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

/**
 * Label view that appears next to FAB buttons.
 *
 * This class is a custom CardView used to create text labels that appear
 * next to [KarsuTitleFab] buttons. Labels are used to explain the function
 * of each FAB button to the user.
 *
 * Features:
 * - Customizable background color
 * - Customizable text color
 * - Customizable corner radius (CardView radius)
 * - Clickable/non-clickable mode support
 * - Ripple effect support (in clickable mode)
 *
 * XML Attributes:
 * - `fab_title_backgroundColor`: Label background color
 * - `fab_title_textColor`: Label text color
 * - `fab_title_cornerRadius`: Corner radius
 *
 * @see KarsuTitleFab
 * @see KarSuFabsMenu
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
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
