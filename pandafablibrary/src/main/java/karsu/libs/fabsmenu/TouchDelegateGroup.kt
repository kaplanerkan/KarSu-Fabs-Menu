package karsu.libs.fabsmenu

import android.graphics.Rect
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View

class TouchDelegateGroup(uselessHackyView: View) : TouchDelegate(USELESS_HACKY_RECT, uselessHackyView) {

    companion object {
        private val USELESS_HACKY_RECT = Rect()
    }

    private val touchDelegates = ArrayList<TouchDelegate>()
    private var currentTouchDelegate: TouchDelegate? = null
    var isEnabled: Boolean = false

    fun addTouchDelegate(touchDelegate: TouchDelegate) {
        touchDelegates.add(touchDelegate)
    }

    fun removeTouchDelegate(touchDelegate: TouchDelegate) {
        touchDelegates.remove(touchDelegate)
        if (currentTouchDelegate == touchDelegate) {
            currentTouchDelegate = null
        }
    }

    fun clearTouchDelegates() {
        touchDelegates.clear()
        currentTouchDelegate = null
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        var delegate: TouchDelegate? = null

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                for (touchDelegate in touchDelegates) {
                    if (touchDelegate.onTouchEvent(event)) {
                        currentTouchDelegate = touchDelegate
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                delegate = currentTouchDelegate
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                delegate = currentTouchDelegate
                currentTouchDelegate = null
            }
        }

        return delegate?.onTouchEvent(event) ?: false
    }
}
