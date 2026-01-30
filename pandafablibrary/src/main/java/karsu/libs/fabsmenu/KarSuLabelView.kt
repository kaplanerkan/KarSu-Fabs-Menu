package karsu.libs.fabsmenu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

/**
 * FAB butonlarının yanında görünen etiket (label) görünümü.
 *
 * Bu sınıf, [KarsuTitleFab] butonlarının yanında görünen metin etiketlerini
 * oluşturmak için kullanılan özel bir CardView'dır. Etiketler, kullanıcıya
 * her FAB butonunun işlevini açıklamak için kullanılır.
 *
 * Özellikler:
 * - Özelleştirilebilir arka plan rengi
 * - Özelleştirilebilir metin rengi
 * - Özelleştirilebilir köşe yuvarlaklığı (CardView radius)
 * - Tıklanabilir/tıklanamaz mod desteği
 * - Ripple efekti desteği (tıklanabilir modda)
 *
 * XML Attributes:
 * - `fab_title_backgroundColor`: Etiket arka plan rengi
 * - `fab_title_textColor`: Etiket metin rengi
 * - `fab_title_cornerRadius`: Köşe yuvarlaklığı
 *
 * @see KarsuTitleFab
 * @see KarSuFabsMenu
 *
 * @author Erkan Kaplan
 * @since 1.0.0
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
