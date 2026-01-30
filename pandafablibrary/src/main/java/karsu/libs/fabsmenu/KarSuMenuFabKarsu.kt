package karsu.libs.fabsmenu

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.net.Uri
import android.util.AttributeSet

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
