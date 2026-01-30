package karsu.libs.fabsmenu

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Container layout that provides an overlay layer for the FAB menu.
 *
 * This class is a FrameLayout that wraps the [KarSuFabsMenu] component and
 * is used to dim the background when the menu is open. The overlay layer
 * is used to draw the user's attention to the open menu and to close the
 * menu when areas outside the menu are tapped.
 *
 * Features:
 * - Customizable overlay color and opacity
 * - Animated show/hide effect
 * - Clickable overlay (to close the menu)
 * - Programmatic open/close control
 *
 * XML Attributes:
 * - `fabs_menu_overlayColor`: Overlay color (e.g., "#99000000")
 * - `fabs_menu_clickableOverlay`: Should menu close when overlay is tapped (default: true)
 *
 * Usage example:
 * ```xml
 * <karsu.libs.fabsmenu.KarSuFabsMenuLayout
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent"
 *     app:fabs_menu_overlayColor="#99000000"
 *     app:fabs_menu_clickableOverlay="true">
 *
 *     <karsu.libs.fabsmenu.KarSuFabsMenu
 *         ... />
 *
 * </karsu.libs.fabsmenu.KarSuFabsMenuLayout>
 * ```
 *
 * @see KarSuFabsMenu
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
class KarSuFabsMenuLayout : FrameLayout {

    companion object {
        private const val TAG = "KarSuFabsMenuLayout"
    }

    var overlayColor: Int = 0
        private set

    var overlayView: View
        private set

    var clickableOverlay: Boolean = true
        private set

    var animationDuration: Int = 500

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    init {
        overlayView = View(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            visibility = View.GONE
        }
        addView(overlayView)
    }

    private fun init(attrs: AttributeSet?) {
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.FABsMenuLayout, 0, 0)
        try {
            overlayColor = attr.getColor(
                R.styleable.FABsMenuLayout_fabs_menu_overlayColor,
                Color.parseColor("#4d000000")
            )
            clickableOverlay = attr.getBoolean(
                R.styleable.FABsMenuLayout_fabs_menu_clickableOverlay,
                true
            )
        } catch (e: Exception) {
            Log.e(TAG, "Failure configuring KarSuFabsMenuLayout overlay", e)
        } finally {
            attr.recycle()
        }
        overlayView.setBackgroundColor(overlayColor)
    }

    fun setOverlayColor(color: Int) {
        overlayView.setBackgroundColor(color)
        this.overlayColor = color
    }

    fun setClickableOverlay(clickable: Boolean) {
        this.clickableOverlay = clickable
    }

    fun show() = toggle(show = true)

    fun show(immediately: Boolean) = toggle(show = true, immediately = immediately)

    fun hide() = toggle(show = false)

    fun hide(immediately: Boolean) = toggle(show = false, immediately = immediately)

    fun toggle(show: Boolean) = toggle(show, immediately = false)

    fun toggle(show: Boolean, immediately: Boolean) = toggle(show, immediately, null)

    fun toggle(show: Boolean, immediately: Boolean, onOverlayClick: OnClickListener?) {
        // Cancel any existing animation to prevent listener accumulation
        overlayView.animate().cancel()
        overlayView.animate().setListener(null)

        if (show) {
            overlayView.alpha = 0f
            overlayView.visibility = VISIBLE
        }

        if (immediately) {
            overlayView.alpha = if (show) 1.0f else 0.0f
            if (!show) {
                overlayView.visibility = GONE
                overlayView.setOnClickListener(null)
            } else if (clickableOverlay) {
                overlayView.setOnClickListener(onOverlayClick)
            }
        } else {
            overlayView.animate()
                .alpha(if (show) 1.0f else 0.0f)
                .setDuration(animationDuration.toLong())
                .withEndAction {
                    if (!show) {
                        overlayView.visibility = GONE
                        overlayView.setOnClickListener(null)
                    } else if (clickableOverlay) {
                        overlayView.setOnClickListener(onOverlayClick)
                    }
                }
                .start()
        }
    }
}
