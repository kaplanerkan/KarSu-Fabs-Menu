package karsu.libs.fabsmenu.compose.animation

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import kotlin.math.pow
import kotlin.math.sin

/**
 * Custom overshoot easing function that creates a bouncy effect.
 */
class OvershootEasing(private val tension: Float = 2.0f) : Easing {
    override fun transform(fraction: Float): Float {
        val t = fraction - 1.0f
        return t * t * ((tension + 1) * t + tension) + 1.0f
    }
}

/**
 * Custom anticipate easing function.
 */
class AnticipateEasing(private val tension: Float = 2.0f) : Easing {
    override fun transform(fraction: Float): Float {
        return fraction * fraction * ((tension + 1) * fraction - tension)
    }
}

/**
 * Custom anticipate-overshoot easing function.
 */
class AnticipateOvershootEasing(private val tension: Float = 2.0f) : Easing {
    override fun transform(fraction: Float): Float {
        val a = tension * 1.5f
        return if (fraction < 0.5f) {
            0.5f * (2.0f * fraction) * (2.0f * fraction) * ((a + 1) * 2.0f * fraction - a)
        } else {
            val t = 2.0f * fraction - 2.0f
            0.5f * (t * t * ((a + 1) * t + a) + 2.0f)
        }
    }
}

/**
 * Elastic easing function for bouncy animations.
 */
class ElasticEasing(
    private val amplitude: Float = 1.0f,
    private val period: Float = 0.3f
) : Easing {
    override fun transform(fraction: Float): Float {
        if (fraction == 0f || fraction == 1f) return fraction
        val p = period
        val a = amplitude.coerceAtLeast(1f)
        val s = p / 4f
        val t = fraction - 1f
        return (a * 2.0.pow((10 * t).toDouble()) *
                sin(((t - s) * (2 * Math.PI) / p).toFloat())).toFloat() * -1f + 1f
    }
}

/**
 * Animation specifications for the FABs menu.
 */
object FabsMenuAnimations {

    /**
     * Default overshoot easing for expand animations.
     */
    val OvershootEasingDefault = OvershootEasing(2.0f)

    /**
     * Anticipate easing for collapse animations.
     */
    val AnticipateEasingDefault = AnticipateEasing(2.0f)

    /**
     * Creates an expand animation spec for menu items.
     */
    fun <T> expandSpec(
        durationMs: Int = 500,
        delayMs: Int = 0
    ): TweenSpec<T> = tween(
        durationMillis = durationMs,
        delayMillis = delayMs,
        easing = OvershootEasingDefault
    )

    /**
     * Creates a collapse animation spec for menu items.
     */
    fun <T> collapseSpec(
        durationMs: Int = 300,
        delayMs: Int = 0
    ): TweenSpec<T> = tween(
        durationMillis = durationMs,
        delayMillis = delayMs,
        easing = FastOutSlowInEasing
    )

    /**
     * Creates a rotation animation spec for the menu button.
     */
    fun <T> rotationSpec(
        durationMs: Int = 300
    ): TweenSpec<T> = tween(
        durationMillis = durationMs,
        easing = FastOutSlowInEasing
    )

    /**
     * Creates an overlay fade animation spec.
     */
    fun <T> overlaySpec(
        durationMs: Int = 300
    ): TweenSpec<T> = tween(
        durationMillis = durationMs,
        easing = LinearOutSlowInEasing
    )

    /**
     * Calculates staggered delay for an item at the given index.
     *
     * @param index Item index in the list.
     * @param totalItems Total number of items.
     * @param baseDelayMs Base delay in milliseconds.
     * @param isExpanding Whether the menu is expanding (true) or collapsing (false).
     * @return Delay in milliseconds for this item.
     */
    fun calculateStaggerDelay(
        index: Int,
        totalItems: Int,
        baseDelayMs: Int = 50,
        isExpanding: Boolean = true
    ): Int {
        return if (isExpanding) {
            index * baseDelayMs
        } else {
            // Reverse order for collapsing
            (totalItems - 1 - index) * baseDelayMs
        }
    }
}
