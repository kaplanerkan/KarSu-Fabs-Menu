package karsu.libs.fabsmenu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Başlık etiketi olan Floating Action Button bileşeni.
 *
 * Bu sınıf, Material Design FloatingActionButton'ı genişleterek yanında
 * özelleştirilebilir bir metin etiketi görüntüleme özelliği ekler.
 * [KarSuFabsMenu] içinde kullanılan alt FAB butonlarını temsil eder.
 *
 * Özellikler:
 * - Özelleştirilebilir başlık metni (maksimum 25 karakter, sonrası "..." ile kesilir)
 * - Özelleştirilebilir etiket arka plan rengi
 * - Özelleştirilebilir etiket metin rengi
 * - Özelleştirilebilir etiket köşe yuvarlaklığı
 * - Özelleştirilebilir etiket padding'i
 * - Etiket tıklama etkinleştirme/devre dışı bırakma
 * - Animasyonlu show/hide desteği
 *
 * XML Attributes:
 * - `fab_title`: Etiket metni
 * - `fab_enableTitleClick`: Etiket tıklanabilir mi (varsayılan: true)
 * - `fab_title_backgroundColor`: Etiket arka plan rengi
 * - `fab_title_textColor`: Etiket metin rengi
 * - `fab_title_cornerRadius`: Etiket köşe yuvarlaklığı
 * - `fab_title_textPadding`: Etiket iç boşluğu
 *
 * Kullanım örneği:
 * ```xml
 * <karsu.libs.fabsmenu.KarsuTitleFab
 *     android:id="@+id/fabEdit"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:src="@drawable/ic_edit"
 *     app:fab_title="Düzenle"
 *     app:fab_title_backgroundColor="@color/white"
 *     app:fab_title_textColor="@color/black"
 *     app:fab_title_cornerRadius="8dp"
 *     app:fab_enableTitleClick="true" />
 * ```
 *
 * @see KarSuFabsMenu
 * @see KarSuLabelView
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 */
open class KarsuTitleFab : FloatingActionButton {

    companion object {
        private const val MAX_CHARACTERS_COUNT = 25
        private val FAST_OUT_LINEAR_IN_INTERPOLATOR = FastOutLinearInInterpolator()
        private val LINEAR_OUT_SLOW_IN_INTERPOLATOR = LinearOutSlowInInterpolator()
        private const val SHOW_HIDE_ANIM_DURATION = 200L
        private const val ANIM_STATE_NONE = 0
        private const val ANIM_STATE_HIDING = 1
        private const val ANIM_STATE_SHOWING = 2
        private const val TAG = "KarsuTitleFab"
    }

    constructor(context: Context) : super(context) {
        initAttributes(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(context, attrs)
    }

    internal var animState = ANIM_STATE_NONE

    private var _title: String? = null

    open var title: String?
        get() {
            val t = _title ?: return null
            return if (t.length > MAX_CHARACTERS_COUNT) {
                "${t.substring(0, MAX_CHARACTERS_COUNT)}..."
            } else {
                t
            }
        }
        set(value) {
            _title = value
            karSuLabelView?.content?.text = value
        }

    var titleClickEnabled: Boolean = true
        set(value) {
            field = value
            karSuLabelView?.isClickable = value
        }

    var titleBackgroundColor: Int = 0
        set(value) {
            field = value
            karSuLabelView?.setBackgroundColor(value)
        }

    var titleTextColor: Int = 0
        set(value) {
            field = value
            karSuLabelView?.content?.setTextColor(value)
        }

    var titleCornerRadius: Float = -1f
        set(value) {
            field = value
            karSuLabelView?.radius = value
        }

    var titleTextPadding: Int = 0
        set(value) {
            field = value
            karSuLabelView?.content?.setPadding(value, value / 2, value, value / 2)
        }

    private var clickListener: OnClickListener? = null

    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TitleFAB, 0, 0)
        try {
            _title = attr.getString(R.styleable.TitleFAB_fab_title)
            titleClickEnabled = attr.getBoolean(R.styleable.TitleFAB_fab_enableTitleClick, true)
            titleBackgroundColor = attr.getInt(
                R.styleable.TitleFAB_fab_title_backgroundColor,
                ContextCompat.getColor(context, android.R.color.white)
            )
            titleTextColor = attr.getInt(
                R.styleable.TitleFAB_fab_title_textColor,
                ContextCompat.getColor(context, android.R.color.black)
            )
            titleCornerRadius = attr.getDimensionPixelSize(
                R.styleable.TitleFAB_fab_title_cornerRadius, -1
            ).toFloat()
            titleTextPadding = attr.getDimensionPixelSize(
                R.styleable.TitleFAB_fab_title_textPadding,
                DimensionUtils.convertDpToPixel(8f, context).toInt()
            )
        } catch (e: Exception) {
            Log.w(TAG, "Failure reading attributes", e)
        } finally {
            attr.recycle()
        }
        setOnClickListener(null)
        size = SIZE_MINI
    }

    override fun setBackgroundColor(color: Int) {
        backgroundTintList = ColorStateList.valueOf(color)
    }

    fun getOnClickListener(): OnClickListener? = clickListener

    override fun setOnClickListener(l: OnClickListener?) {
        this.clickListener = l
        isClickable = l != null
        super.setOnClickListener(l)
    }

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        isFocusable = clickable
        karSuLabelView?.setOnClickListener(if (titleClickEnabled && clickable) clickListener else null)
    }

    internal val karSuLabelView: KarSuLabelView?
        get() = getTag(R.id.fab_label) as? KarSuLabelView

    private fun labelIsOrWillBeShown(): Boolean {
        val label = karSuLabelView ?: return false
        return if (label.visibility != View.VISIBLE) {
            animState == ANIM_STATE_SHOWING
        } else {
            animState != ANIM_STATE_HIDING
        }
    }

    private fun labelIsOrWillBeHidden(): Boolean {
        val label = karSuLabelView ?: return true
        return if (label.visibility == View.VISIBLE) {
            animState == ANIM_STATE_HIDING
        } else {
            animState != ANIM_STATE_SHOWING
        }
    }

    override fun show() {
        val label = karSuLabelView
        if (label == null) {
            super.show()
            return
        }

        if (labelIsOrWillBeShown()) return

        label.animate().cancel()

        if (shouldAnimateVisibilityChange()) {
            animState = ANIM_STATE_SHOWING

            if (label.visibility != View.VISIBLE) {
                label.alpha = 0f
                label.scaleY = 0f
                label.scaleX = 0f
            }

            label.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(SHOW_HIDE_ANIM_DURATION)
                .setInterpolator(LINEAR_OUT_SLOW_IN_INTERPOLATOR)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super@KarsuTitleFab.show()
                        label.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        animState = ANIM_STATE_NONE
                    }
                })
        } else {
            label.visibility = View.VISIBLE
            label.alpha = 1f
            label.scaleY = 1f
            label.scaleX = 1f
        }
    }

    override fun hide() {
        val label = karSuLabelView
        if (label == null) {
            super.hide()
            return
        }

        if (labelIsOrWillBeHidden()) return

        label.animate().cancel()

        if (shouldAnimateVisibilityChange()) {
            animState = ANIM_STATE_HIDING

            label.animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(SHOW_HIDE_ANIM_DURATION)
                .setInterpolator(FAST_OUT_LINEAR_IN_INTERPOLATOR)
                .setListener(object : AnimatorListenerAdapter() {
                    private var cancelled = false

                    override fun onAnimationStart(animation: Animator) {
                        super@KarsuTitleFab.hide()
                        label.visibility = View.VISIBLE
                        cancelled = false
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        cancelled = true
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        animState = ANIM_STATE_NONE
                        if (!cancelled) {
                            label.visibility = View.GONE
                        }
                    }
                })
        } else {
            label.visibility = View.GONE
        }
    }

    private fun shouldAnimateVisibilityChange(): Boolean {
        val label = karSuLabelView
        return if (label != null) {
            isLaidOut && label.isLaidOut && !isInEditMode
        } else {
            isLaidOut && !isInEditMode
        }
    }
}
