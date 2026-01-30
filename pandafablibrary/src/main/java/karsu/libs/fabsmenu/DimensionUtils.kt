package karsu.libs.fabsmenu

import android.content.Context
import android.util.DisplayMetrics

/**
 * Boyut dönüşüm yardımcı sınıfı.
 *
 * Bu utility sınıfı, dp (density-independent pixels) ve px (pixels) arasında
 * dönüşüm yapmak için kullanılır. Android'de farklı ekran yoğunluklarında
 * tutarlı görünüm sağlamak için dp değerlerinin piksel değerlerine
 * dönüştürülmesi gerekir.
 *
 * @author Erkan Kaplan
 * @since 1.0.0
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
