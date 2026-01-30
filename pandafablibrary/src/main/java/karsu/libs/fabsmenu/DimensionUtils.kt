package karsu.libs.fabsmenu

import android.content.Context
import android.util.DisplayMetrics

/**
 * Dimension conversion utility class.
 *
 * This utility class is used for converting between dp (density-independent pixels)
 * and px (pixels). In Android, dp values need to be converted to pixel values
 * to ensure consistent appearance across different screen densities.
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
internal object DimensionUtils {

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
