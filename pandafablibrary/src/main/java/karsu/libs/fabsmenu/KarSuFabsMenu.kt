package karsu.libs.fabsmenu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.graphics.withRotation
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isGone
import karsu.libs.fabsmenu.enums.ExpandDirection
import karsu.libs.fabsmenu.enums.LabelsPosition

/**
 * Genişletilebilir Floating Action Button menü bileşeni.
 *
 * Bu sınıf, Material Design 3 uyumlu, animasyonlu ve özelleştirilebilir bir
 * FAB menüsü oluşturur. Ana menü butonuna tıklandığında, alt FAB butonları
 * belirlenen yönde animasyonlu olarak açılır.
 *
 * Temel Özellikler:
 * - 4 farklı açılma yönü: UP, DOWN, LEFT, RIGHT
 * - Özelleştirilebilir etiket konumu: LEFT, RIGHT
 * - Animasyonlu açılma/kapanma efektleri (OvershootInterpolator)
 * - Dönen menü butonu ikonu (135° rotasyon)
 * - RecyclerView entegrasyonu (scroll'da otomatik gizleme)
 * - Durum kaydetme/geri yükleme desteği
 * - Overlay entegrasyonu ([KarSuFabsMenuLayout] ile)
 *
 * XML Attributes:
 * - `fab_moreButtonPlusIcon`: Menü butonu ikonu
 * - `fab_moreButtonBackgroundColor`: Menü butonu arka plan rengi
 * - `fab_moreButtonRippleColor`: Menü butonu ripple rengi
 * - `fab_moreButtonSize`: Menü butonu boyutu (normal, mini, auto)
 * - `fab_moreButtonCustomSize`: Özel menü butonu boyutu (dp)
 * - `fab_moreButtonIconSize`: Menü butonu ikon boyutu (dp)
 * - `fab_moreButtonIconTint`: Menü butonu ikon tint rengi
 * - `fab_menuMargins`: Menü kenar boşlukları
 * - `fab_expandDirection`: Açılma yönü (up, down, left, right)
 * - `fab_labelsPosition`: Etiket konumu (left, right)
 *
 * Kullanım örneği:
 * ```xml
 * <karsu.libs.fabsmenu.KarSuFabsMenu
 *     android:id="@+id/fabsMenu"
 *     android:layout_width="wrap_content"
 *     android:layout_height="wrap_content"
 *     android:layout_gravity="bottom|end"
 *     app:fab_moreButtonPlusIcon="@drawable/ic_plus"
 *     app:fab_moreButtonBackgroundColor="@color/turquoise"
 *     app:fab_expandDirection="up"
 *     app:fab_labelsPosition="left">
 *
 *     <karsu.libs.fabsmenu.KarsuTitleFab
 *         android:id="@+id/fabEdit"
 *         app:fab_title="Düzenle"
 *         ... />
 *
 * </karsu.libs.fabsmenu.KarSuFabsMenu>
 * ```
 *
 * Programatik Kullanım:
 * ```kotlin
 * // Menüyü aç/kapat
 * fabsMenu.expand()
 * fabsMenu.collapse()
 * fabsMenu.toggle()
 *
 * // Ayarları güncelle
 * fabsMenu.updateExpandDirection(ExpandDirection.DOWN)
 * fabsMenu.updateLabelsPosition(LabelsPosition.RIGHT)
 * fabsMenu.setAnimationDuration(300)
 * fabsMenu.updateAllLabelsBackgroundColor(Color.WHITE)
 * ```
 *
 * İç Sınıflar:
 * - [RotatingDrawable]: Menü butonu için dönen ikon drawable'ı
 * - [SavedState]: Menü durumunu kaydetmek için Parcelable state sınıfı
 * - [LayoutParams]: Animasyon parametrelerini tutan özel layout params
 *
 * @see KarSuFabsMenuLayout
 * @see KarsuTitleFab
 * @see KarSuFabsMenuListener
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 */
@Suppress("unused")
class KarSuFabsMenu : ViewGroup {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    companion object {
        private const val TAG = "KarSuFabsMenu"
        private const val COLLAPSED_PLUS_ROTATION = 0f
        private const val EXPANDED_PLUS_ROTATION = 135f

        private val expandInterpolator: Interpolator = OvershootInterpolator()
        private val collapseInterpolator: Interpolator = DecelerateInterpolator(3f)
        private val alphaExpandInterpolator: Interpolator = DecelerateInterpolator()
    }

    var animationDuration: Int = 500
    var menuMargins: Int = 0
        private set
    var menuTopMargin: Int = 0
    var menuBottomMargin: Int = 0
    var menuRightMargin: Int = 0
    var menuLeftMargin: Int = 0

    var menuButtonColor: Int = 0
        private set

    var menuButtonRippleColor: Int = 0
        private set

    var menuButtonSize: Int = 0
        private set

    var menuButtonCustomSize: Int = 0
        private set

    var menuButtonIconSize: Int = 0
        private set

    var menuButtonIconTint: Int = 0
        private set

    var expandDirection: ExpandDirection = ExpandDirection.UP
        private set
    lateinit var menuButton: KarSuMenuFabKarsu
        private set
    var menuButtonIcon: Drawable? = null
        private set
    var rotatingDrawable: RotatingDrawable? = null
        private set

    var buttonSpacing: Int = 0
    var labelsMargin: Int = 0
    var labelsVerticalOffset: Int = 0
    var labelsPosition: LabelsPosition = LabelsPosition.LEFT
        private set

    var buttonsCount: Int = 0
        private set
    var maxButtonWidth: Int = 0
    var maxButtonHeight: Int = 0

    private lateinit var touchDelegateGroup: TouchDelegateGroup
    var menuListener: KarSuFabsMenuListener? = null

    var isExpanded: Boolean = false
        private set

    private var animating = false
    private val expandAnimation = AnimatorSet().apply { duration = animationDuration.toLong() }
    private val collapseAnimation = AnimatorSet().apply { duration = animationDuration.toLong() }

    private val collapseListener = object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            setMenuButtonsClickable(clickable = false)
        }

        override fun onAnimationEnd(animation: Animator) {
            setMenuButtonsVisibility(visible = false)
            animating = false
            isExpanded = false
            requestLayout()
        }
    }

    private val expandListener = object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            setMenuButtonsVisibility(visible = true)
        }

        override fun onAnimationEnd(animation: Animator) {
            setMenuButtonsClickable(clickable = true)
            animating = false
        }
    }

    private fun init(attrs: AttributeSet?) {
        buttonSpacing = DimensionUtils.convertDpToPixel(16f, context).toInt()
        labelsMargin = resources.getDimensionPixelSize(R.dimen.fab_labels_margin)
        labelsVerticalOffset = DimensionUtils.convertDpToPixel(-1.5f, context).toInt()

        touchDelegateGroup = TouchDelegateGroup(this)
        setTouchDelegate(touchDelegateGroup)

        val attr = context.obtainStyledAttributes(attrs, R.styleable.FABsMenu, 0, 0)
        try {
            menuMargins = attr.getDimensionPixelSize(R.styleable.FABsMenu_fab_menuMargins, 0)
            val defaultMargin = DimensionUtils.convertDpToPixel(16f, context).toInt()

            menuTopMargin = attr.getDimensionPixelSize(
                R.styleable.FABsMenu_fab_menuTopMargin,
                if (menuMargins != 0) menuMargins else defaultMargin
            )
            menuBottomMargin = attr.getDimensionPixelSize(
                R.styleable.FABsMenu_fab_menuBottomMargin,
                if (menuMargins != 0) menuMargins else defaultMargin
            )
            menuRightMargin = attr.getDimensionPixelSize(
                R.styleable.FABsMenu_fab_menuRightMargin,
                if (menuMargins != 0) menuMargins else defaultMargin
            )
            menuLeftMargin = attr.getDimensionPixelSize(
                R.styleable.FABsMenu_fab_menuLeftMargin,
                if (menuMargins != 0) menuMargins else defaultMargin
            )

            val drawableId = attr.getResourceId(R.styleable.FABsMenu_fab_moreButtonPlusIcon, 0)
            if (drawableId != 0) {
                menuButtonIcon = VectorDrawableCompat.create(resources, drawableId, context.theme)
            }

            menuButtonColor = attr.getColor(
                R.styleable.FABsMenu_fab_moreButtonBackgroundColor,
                getColor(android.R.color.holo_blue_dark)
            )
            menuButtonRippleColor = attr.getColor(
                R.styleable.FABsMenu_fab_moreButtonRippleColor,
                getColor(android.R.color.holo_blue_light)
            )
            menuButtonSize = attr.getInt(R.styleable.FABsMenu_fab_moreButtonSize, FloatingActionButton.SIZE_NORMAL)
            menuButtonCustomSize = attr.getDimensionPixelSize(R.styleable.FABsMenu_fab_moreButtonCustomSize, 0)
            menuButtonIconSize = attr.getDimensionPixelSize(R.styleable.FABsMenu_fab_moreButtonIconSize, 0)
            menuButtonIconTint = attr.getColor(R.styleable.FABsMenu_fab_moreButtonIconTint, 0)
            expandDirection = ExpandDirection.fromOrdinal(
                attr.getInt(R.styleable.FABsMenu_fab_expandDirection, ExpandDirection.UP.ordinal)
            )
            labelsPosition = LabelsPosition.fromOrdinal(
                attr.getInt(
                    R.styleable.FABsMenu_fab_labelsPosition,
                    if (isRtl()) LabelsPosition.RIGHT.ordinal else LabelsPosition.LEFT.ordinal
                )
            )
        } catch (e: Exception) {
            Log.w(TAG, "Failure reading attributes", e)
        } finally {
            attr.recycle()
        }

        if (menuListener == null) {
            menuListener = object : KarSuFabsMenuListener() {}
        }

        menuButton = createMenuButton(context)
    }

    private fun expandsHorizontally(): Boolean =
        expandDirection == ExpandDirection.LEFT || expandDirection == ExpandDirection.RIGHT

    private fun createMenuButton(context: Context): KarSuMenuFabKarsu {
        val button = KarSuMenuFabKarsu(context)

        button.backgroundTintList = ColorStateList.valueOf(menuButtonColor)
        button.rippleColor = menuButtonRippleColor
        button.id = R.id.fab_expand_menu_button

        if (menuButtonCustomSize > 0) {
            button.customSize = menuButtonCustomSize
        } else {
            button.size = menuButtonSize
        }

        if (menuButtonIconSize > 0) {
            button.setMaxImageSize(menuButtonIconSize)
        }

        if (menuButtonIconTint != 0) {
            button.imageTintList = ColorStateList.valueOf(menuButtonIconTint)
        }

        button.setOnClickListener {
            menuListener?.onMenuClicked(this)
        }

        if (menuButtonIcon != null) {
            createRotatingDrawable(button)
        }

        addView(button, super.generateDefaultLayoutParams())
        buttonsCount++
        return button
    }

    private fun createRotatingDrawable(button: KarSuMenuFabKarsu) {
        val icon = menuButtonIcon ?: return
        val dr = RotatingDrawable(icon)
        val interpolator = OvershootInterpolator()

        val collapseAnimator = ObjectAnimator.ofFloat(dr, "rotation", EXPANDED_PLUS_ROTATION, COLLAPSED_PLUS_ROTATION)
        val expandAnimator = ObjectAnimator.ofFloat(dr, "rotation", COLLAPSED_PLUS_ROTATION, EXPANDED_PLUS_ROTATION)

        collapseAnimator.interpolator = interpolator
        expandAnimator.interpolator = interpolator

        expandAnimation.play(expandAnimator)
        collapseAnimation.play(collapseAnimator)

        button.setImageDrawable(dr)
        rotatingDrawable = dr
    }

    fun addAllButtons(vararg buttons: KarsuTitleFab) {
        buttons.forEach { addButton(it) }
    }

    fun addButton(button: KarsuTitleFab, index: Int = buttonsCount - 1) {
        require(buttonsCount < 6) { "A floating action buttons menu should have no more than six options." }
        addView(button, index)
        buttonsCount++
        createLabels()
        if (buttonsCount > 1 && visibility != VISIBLE) {
            show(expand = false)
        }
        if (buttonsCount < 3) {
            Log.w(TAG, "A floating action buttons menu should have at least three options")
        }
    }

    fun removeButton(index: Int) {
        val child = getChildAt(index) ?: return
        when (child) {
            is KarSuMenuFabKarsu -> return
            is KarsuTitleFab -> removeButton(child)
        }
    }

    fun removeButton(button: KarsuTitleFab) {
        try {
            button.hide()
            removeView(button.karSuLabelView)
            removeView(button)
            button.setTag(R.id.fab_label, null)
            buttonsCount--
            if (buttonsCount <= 1) hide()
        } catch (e: Exception) {
            Log.e(TAG, "Failure removing Button", e)
        }
    }

    fun removeAllButtons() {
        for (i in 0..childCount) {
            removeButton(0)
        }
    }

    private fun getColor(id: Int): Int = ContextCompat.getColor(context, id)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        maxButtonWidth = menuButton.measuredWidth
        maxButtonHeight = menuButton.measuredHeight

        // When collapsed, only measure the menu button size
        if (!isExpanded) {
            val width = maxButtonWidth + menuLeftMargin + menuRightMargin
            val height = maxButtonHeight + menuTopMargin + menuBottomMargin
            setMeasuredDimension(width, height)
            return
        }

        // When expanded, measure full size
        var width = buttonSpacing
        var height = buttonSpacing
        var maxLabelWidth = 0

        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child.isGone) continue

            when (expandDirection) {
                ExpandDirection.UP, ExpandDirection.DOWN -> {
                    maxButtonWidth = maxOf(maxButtonWidth, child.measuredWidth)
                    height += child.measuredHeight
                }
                ExpandDirection.LEFT, ExpandDirection.RIGHT -> {
                    width += child.measuredWidth
                    maxButtonHeight = maxOf(maxButtonHeight, child.measuredHeight)
                }
            }

            if (!expandsHorizontally()) {
                val label = child.getTag(R.id.fab_label) as? KarSuLabelView
                if (label != null) {
                    maxLabelWidth = maxOf(maxLabelWidth, label.measuredWidth)
                }
            }
        }

        if (!expandsHorizontally()) {
            width = maxButtonWidth + if (maxLabelWidth > 0) maxLabelWidth + labelsMargin else 0
        } else {
            height = maxButtonHeight
        }

        when (expandDirection) {
            ExpandDirection.UP, ExpandDirection.DOWN -> {
                height += buttonSpacing * (buttonsCount - 1)
                height = adjustForOvershoot(height)
            }
            ExpandDirection.LEFT, ExpandDirection.RIGHT -> {
                width += buttonSpacing * (buttonsCount - 1)
                width = adjustForOvershoot(width)
            }
        }

        height += maxOf(menuTopMargin, menuBottomMargin) * 2
        width += maxOf(menuLeftMargin, menuRightMargin) * 2

        setMeasuredDimension(width, height)
    }

    private fun adjustForOvershoot(dimension: Int): Int = dimension * 12 / 10

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        when (expandDirection) {
            ExpandDirection.UP, ExpandDirection.DOWN -> layoutVertical(l, t, r, b)
            ExpandDirection.LEFT, ExpandDirection.RIGHT -> layoutHorizontal(l, t, r, b)
        }
    }

    private fun layoutVertical(l: Int, t: Int, r: Int, b: Int) {
        val expandUp = expandDirection == ExpandDirection.UP
        touchDelegateGroup.clearTouchDelegates()

        var addButtonY = if (expandUp) b - t - menuButton.measuredHeight else 0
        if (expandUp) addButtonY -= menuBottomMargin else addButtonY += menuTopMargin

        var buttonsHorizontalCenter = if (labelsPosition == LabelsPosition.LEFT) {
            r - l - maxButtonWidth / 2
        } else {
            maxButtonWidth / 2
        }
        buttonsHorizontalCenter -= if (labelsPosition == LabelsPosition.LEFT) menuRightMargin else -menuLeftMargin

        val addButtonLeft = buttonsHorizontalCenter - menuButton.measuredWidth / 2
        menuButton.layout(
            addButtonLeft, addButtonY,
            addButtonLeft + menuButton.measuredWidth,
            addButtonY + menuButton.measuredHeight
        )

        val labelsOffset = maxButtonWidth / 2 + labelsMargin
        val labelsXNearButton = if (labelsPosition == LabelsPosition.LEFT) {
            buttonsHorizontalCenter - labelsOffset
        } else {
            buttonsHorizontalCenter + labelsOffset
        }

        var nextY = if (expandUp) {
            addButtonY - buttonSpacing
        } else {
            addButtonY + menuButton.measuredHeight + buttonSpacing
        }

        for (i in buttonsCount - 1 downTo 0) {
            val child = getChildAt(i)
            if (child == menuButton || child.isGone) continue

            val childX = buttonsHorizontalCenter - child.measuredWidth / 2
            val childY = if (expandUp) nextY - child.measuredHeight else nextY
            child.layout(childX, childY, childX + child.measuredWidth, childY + child.measuredHeight)

            val collapsedTranslation = (addButtonY - childY).toFloat()
            val expandedTranslation = 0f

            child.translationY = if (isExpanded) expandedTranslation else collapsedTranslation
            child.alpha = if (isExpanded) 1f else 0f

            val params = child.layoutParams as LayoutParams
            params.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation)
            params.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation)
            params.setAnimationsTarget(child)

            val label = child.getTag(R.id.fab_label) as? View
            if (label != null) {
                val labelXAwayFromButton = if (labelsPosition == LabelsPosition.LEFT) {
                    labelsXNearButton - label.measuredWidth
                } else {
                    labelsXNearButton + label.measuredWidth
                }
                val labelLeft = if (labelsPosition == LabelsPosition.LEFT) labelXAwayFromButton else labelsXNearButton
                val labelRight = if (labelsPosition == LabelsPosition.LEFT) labelsXNearButton else labelXAwayFromButton
                val labelTop = childY - labelsVerticalOffset + (child.measuredHeight - label.measuredHeight) / 2

                label.layout(labelLeft, labelTop, labelRight, labelTop + label.measuredHeight)

                val touchArea = Rect(
                    minOf(childX, labelLeft),
                    childY - buttonSpacing / 2,
                    maxOf(childX + child.measuredWidth, labelRight),
                    childY + child.measuredHeight + buttonSpacing / 2
                )
                touchDelegateGroup.addTouchDelegate(TouchDelegate(touchArea, child))

                label.translationY = if (isExpanded) expandedTranslation else collapsedTranslation
                label.alpha = if (isExpanded) 1f else 0f

                val labelParams = label.layoutParams as LayoutParams
                labelParams.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation)
                labelParams.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation)
                labelParams.setAnimationsTarget(label)
            }

            nextY = if (expandUp) childY - buttonSpacing else childY + child.measuredHeight + buttonSpacing
        }
    }

    private fun layoutHorizontal(l: Int, t: Int, r: Int, b: Int) {
        val expandLeft = expandDirection == ExpandDirection.LEFT

        var addButtonX = if (expandLeft) r - l - menuButton.measuredWidth else 0
        if (expandLeft) addButtonX -= menuRightMargin else addButtonX += menuLeftMargin

        var addButtonTop = b - t - maxButtonHeight + (maxButtonHeight - menuButton.measuredHeight) / 2
        addButtonTop -= menuBottomMargin

        menuButton.layout(
            addButtonX, addButtonTop,
            addButtonX + menuButton.measuredWidth,
            addButtonTop + menuButton.measuredHeight
        )

        var nextX = if (expandLeft) {
            addButtonX - buttonSpacing
        } else {
            addButtonX + menuButton.measuredWidth + buttonSpacing
        }

        for (i in buttonsCount - 1 downTo 0) {
            val child = getChildAt(i)
            if (child == menuButton || child.isGone) continue

            val childX = if (expandLeft) nextX - child.measuredWidth else nextX
            val childY = addButtonTop + (menuButton.measuredHeight - child.measuredHeight) / 2
            child.layout(childX, childY, childX + child.measuredWidth, childY + child.measuredHeight)

            val collapsedTranslation = (addButtonX - childX).toFloat()
            val expandedTranslation = 0f

            child.translationX = if (isExpanded) expandedTranslation else collapsedTranslation
            child.alpha = if (isExpanded) 1f else 0f

            val params = child.layoutParams as LayoutParams
            params.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation)
            params.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation)
            params.setAnimationsTarget(child)

            nextX = if (expandLeft) childX - buttonSpacing else childX + child.measuredWidth + buttonSpacing
        }
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams =
        LayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams =
        LayoutParams(super.generateLayoutParams(attrs))

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams =
        LayoutParams(super.generateLayoutParams(p))

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean = super.checkLayoutParams(p)

    override fun onFinishInflate() {
        super.onFinishInflate()
        bringChildToFront(menuButton)
        buttonsCount = childCount
        createLabels()
    }

    private fun createLabels() {
        if (expandsHorizontally()) {
            Log.e("FABs Menu", "FABs menu items can't have labels when the menu expands horizontally")
            return
        }

        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child !is KarsuTitleFab || child is KarSuMenuFabKarsu) continue

            val title = child.title
            if (title.isNullOrEmpty() || child.getTag(R.id.fab_label) != null) continue

            val labelText = TextView(context).apply {
                text = child.title
                setTextColor(child.titleTextColor)
                val padding = child.titleTextPadding
                setPadding(padding, padding / 2, padding, padding / 2)
            }

            val label = KarSuLabelView(context, null, 0, child.titleBackgroundColor).apply {
                id = i + 1
                if (child.titleCornerRadius != -1f) {
                    radius = child.titleCornerRadius
                }
                addView(labelText)
                content = labelText
            }
            addView(label)
            child.setTag(R.id.fab_label, label)
        }
    }

    private fun toggleOverlay(show: Boolean, immediately: Boolean) {
        val parent = parent
        if (parent is KarSuFabsMenuLayout) {
            parent.toggle(show, immediately) { collapse() }
        }
    }

    fun collapse() = collapse(immediately = false)

    fun collapseImmediately() = collapse(immediately = true)

    private fun collapse(immediately: Boolean) {
        if (animating || !isExpanded) return

        animating = true
        touchDelegateGroup.isEnabled = false
        toggleOverlay(show = false, immediately = immediately)
        collapseAnimation.duration = if (immediately) 0L else animationDuration.toLong()
        collapseAnimation.removeListener(collapseListener)
        collapseAnimation.addListener(collapseListener)
        collapseAnimation.start()
        expandAnimation.cancel()
        menuListener?.onMenuCollapsed(this)
    }

    fun toggle() {
        if (isExpanded) collapse() else expand()
    }

    fun expand() {
        if (animating || isExpanded) return

        animating = true
        isExpanded = true
        requestLayout()
        touchDelegateGroup.isEnabled = true
        toggleOverlay(show = true, immediately = false)
        collapseAnimation.cancel()
        expandAnimation.removeListener(expandListener)
        expandAnimation.addListener(expandListener)
        expandAnimation.start()
        menuListener?.onMenuExpanded(this)
    }

    private fun isRtl(): Boolean = resources.getBoolean(R.bool.is_right_to_left)

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        menuButton.isEnabled = enabled
    }

    fun setMenuButtonsVisibility(visible: Boolean) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                if (visible) child.show() else child.hide()
            }
        }
    }

    fun setMenuButtonsClickable(clickable: Boolean) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                child.isClickable = clickable
            }
        }
    }

    fun attachToRecyclerView(rv: RecyclerView) {
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    if (isExpanded) collapse(immediately = true)
                    menuButton.hide()
                } else {
                    menuButton.show()
                }
            }
        })
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return SavedState(superState).apply { expanded = isExpanded }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            isExpanded = state.expanded
            touchDelegateGroup.isEnabled = isExpanded
            rotatingDrawable?.rotation = if (isExpanded) EXPANDED_PLUS_ROTATION else COLLAPSED_PLUS_ROTATION
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    @Suppress("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean = false

    fun show(expand: Boolean = false) {
        visibility = VISIBLE
        menuButton.show()
        if (expand) expand()
    }

    fun hide(collapse: Boolean = true) {
        if (collapse) collapse()
        menuButton.hide()
        visibility = GONE
    }

    fun setOverlayColor(color: Int) {
        (parent as? KarSuFabsMenuLayout)?.setOverlayColor(color)
    }

    fun setMenuMargins(margins: Int) {
        menuMargins = margins
        menuTopMargin = margins
        menuBottomMargin = margins
        menuLeftMargin = margins
        menuRightMargin = margins
        requestLayout()
    }

    fun setMenuButtonColor(color: Int) {
        menuButton.setBackgroundColor(color)
        menuButtonColor = color
    }

    fun setMenuButtonRippleColor(color: Int) {
        menuButton.rippleColor = color
        menuButtonRippleColor = color
    }

    fun setMenuButtonSize(size: Int) {
        menuButton.size = size
        menuButtonSize = size
        requestLayout()
    }

    fun updateExpandDirection(direction: ExpandDirection) {
        expandDirection = direction
        requestLayout()
    }

    fun updateAllLabelsBackgroundColor(color: Int) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                child.titleBackgroundColor = color
            }
        }
    }

    fun updateAllLabelsTextColor(color: Int) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                child.titleTextColor = color
            }
        }
    }

    fun updateAllLabelsCornerRadius(radius: Float) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                child.titleCornerRadius = radius
            }
        }
    }

    fun updateAllLabelsTextPadding(padding: Int) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                child.titleTextPadding = padding
            }
        }
    }

    fun updateAllLabelsTitleClickEnabled(enabled: Boolean) {
        for (i in 0 until buttonsCount) {
            val child = getChildAt(i)
            if (child is KarsuTitleFab && child !is KarSuMenuFabKarsu) {
                child.titleClickEnabled = enabled
            }
        }
    }

    fun setMenuButtonIcon(bitmap: Bitmap) {
        try {
            setMenuButtonIcon(bitmap.toDrawable(resources))
        } catch (e: Exception) {
            Log.e(TAG, "Failure setting MenuButton icon", e)
        }
    }

    fun setMenuButtonIcon(uri: Uri) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val icon = Drawable.createFromStream(inputStream, uri.toString())
                icon?.let { setMenuButtonIcon(it) }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failure setting MenuButton icon", e)
        }
    }

    fun setMenuButtonIcon(resId: Int) {
        try {
            val icon = ContextCompat.getDrawable(context, resId)
                ?: throw NullPointerException("The icon you try to assign to KarSuFabsMenu does not exist")
            setMenuButtonIcon(icon)
        } catch (e: Exception) {
            Log.e(TAG, "Failure setting MenuButton icon", e)
        }
    }

    fun setMenuButtonIcon(icon: Drawable) {
        menuButton.setImageDrawable(icon)
        menuButtonIcon = icon
        createRotatingDrawable(menuButton)
    }

    fun updateLabelsPosition(position: LabelsPosition) {
        labelsPosition = position
        requestLayout()
    }

    fun setAnimationDuration(duration: Int, applyToOverlay: Boolean = true) {
        if (applyToOverlay) {
            (parent as? KarSuFabsMenuLayout)?.animationDuration = duration
        }
        animationDuration = duration
    }

    class RotatingDrawable(drawable: Drawable) : LayerDrawable(arrayOf(drawable)) {
        var rotation: Float = 0f
            set(value) {
                field = value
                invalidateSelf()
            }

        override fun draw(canvas: Canvas) {
                canvas.withRotation(rotation, bounds.centerX().toFloat(), bounds.centerY().toFloat()) {
                super.draw(this)
            }
        }
    }

    class SavedState : BaseSavedState {
        var expanded: Boolean = false

        constructor(superState: Parcelable?) : super(superState)

        private constructor(parcel: Parcel) : super(parcel) {
            expanded = parcel.readInt() == 1
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(if (expanded) 1 else 0)
        }

        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState = SavedState(parcel)
            override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)
        }
    }

    private inner class LayoutParams(source: ViewGroup.LayoutParams) : ViewGroup.LayoutParams(source) {
        val mExpandDir = ObjectAnimator()
        val mExpandAlpha = ObjectAnimator()
        val mCollapseDir = ObjectAnimator()
        val mCollapseAlpha = ObjectAnimator()
        private var animationsSetToPlay = false

        init {
            mExpandDir.interpolator = expandInterpolator
            mExpandAlpha.interpolator = alphaExpandInterpolator
            mCollapseDir.interpolator = collapseInterpolator
            mCollapseAlpha.interpolator = collapseInterpolator

            mCollapseAlpha.setProperty(ALPHA)
            mCollapseAlpha.setFloatValues(1f, 0f)
            mExpandAlpha.setProperty(ALPHA)
            mExpandAlpha.setFloatValues(0f, 1f)

            when (expandDirection) {
                ExpandDirection.UP, ExpandDirection.DOWN -> {
                    mCollapseDir.setProperty(TRANSLATION_Y)
                    mExpandDir.setProperty(TRANSLATION_Y)
                }
                ExpandDirection.LEFT, ExpandDirection.RIGHT -> {
                    mCollapseDir.setProperty(TRANSLATION_X)
                    mExpandDir.setProperty(TRANSLATION_X)
                }
            }
        }

        fun setAnimationsTarget(view: View) {
            mCollapseAlpha.target = view
            mCollapseDir.target = view
            mExpandAlpha.target = view
            mExpandDir.target = view

            if (!animationsSetToPlay) {
                addLayerTypeListener(mExpandDir, view)
                addLayerTypeListener(mCollapseDir, view)

                collapseAnimation.play(mCollapseAlpha)
                collapseAnimation.play(mCollapseDir)
                expandAnimation.play(mExpandAlpha)
                expandAnimation.play(mExpandDir)
                animationsSetToPlay = true
            }
        }

        private fun addLayerTypeListener(animator: Animator, view: View) {
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.setLayerType(LAYER_TYPE_NONE, null)
                }

                override fun onAnimationStart(animation: Animator) {
                    view.setLayerType(LAYER_TYPE_HARDWARE, null)
                }
            })
        }
    }
}
