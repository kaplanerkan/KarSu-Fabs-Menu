package karsu.libs.fabsmenu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.net.Uri
import android.util.AttributeSet

/**
 * Main button component of the FAB menu.
 *
 * This class represents the main menu button within [KarSuFabsMenu].
 * When the user taps this button, the menu opens or closes.
 * It is derived from [KarsuTitleFab] but the title feature is disabled.
 *
 * Features:
 * - Rotating icon animation when menu opens (135° rotation)
 * - Customizable background color
 * - Customizable ripple color
 * - Customizable size (normal, mini, or custom)
 * - Customizable icon size and tint color
 *
 * IMPORTANT: Icon settings should not be made directly on this class.
 * Use [KarSuFabsMenu.setMenuButtonIcon] method for icon changes.
 *
 * @see KarSuFabsMenu
 * @see KarsuTitleFab
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
class KarSuMenuFabKarsu : KarsuTitleFab {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override var title: String?
        get() = null
        set(_) { /* Menu button doesn't have a title */ }

    override fun setImageBitmap(bm: Bitmap?) {
        throw UnsupportedOperationException(
            "Don't set the bitmap for menu button using this method. " +
                "Use FABs Menu setMenuButtonIcon() method instead."
        )
    }

    override fun setImageIcon(icon: Icon?) {
        throw UnsupportedOperationException(
            "This method is not available for now. " +
                "Use FABs Menu setMenuButtonIcon() method instead."
        )
    }

    override fun setImageURI(uri: Uri?) {
        throw UnsupportedOperationException(
            "Don't set the uri for menu button using this method. " +
                "Use FABs Menu setMenuButtonIcon() method instead."
        )
    }

    override fun setImageResource(resId: Int) {
        throw UnsupportedOperationException(
            "Don't set the resource for menu button using this method. " +
                "Use FABs Menu setMenuButtonIcon() method instead."
        )
    }
}
