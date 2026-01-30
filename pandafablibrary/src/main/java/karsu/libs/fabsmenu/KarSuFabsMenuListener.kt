package karsu.libs.fabsmenu

/**
 * FAB menüsü olaylarını dinleyen abstract listener sınıfı.
 *
 * Bu sınıf, [KarSuFabsMenu] bileşeninin açılma, kapanma ve tıklama
 * olaylarını dinlemek için kullanılır. Uygulamalar bu sınıfı extend
 * ederek menü durumu değişikliklerine tepki verebilir.
 *
 * Kullanım örneği:
 * ```kotlin
 * fabsMenu.menuListener = object : KarSuFabsMenuListener() {
 *     override fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {
 *         // Menü açıldığında yapılacak işlemler
 *         Log.d("FABsMenu", "Menü açıldı")
 *     }
 *
 *     override fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {
 *         // Menü kapandığında yapılacak işlemler
 *         Log.d("FABsMenu", "Menü kapandı")
 *     }
 * }
 * ```
 *
 * Bellek Sızıntısı Önleme:
 * Activity/Fragment destroy edildiğinde listener'ı null yapın:
 * ```kotlin
 * override fun onDestroy() {
 *     super.onDestroy()
 *     fabsMenu.menuListener = null
 * }
 * ```
 *
 * @see KarSuFabsMenu
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 */
abstract class KarSuFabsMenuListener {

    open fun onMenuClicked(fabsMenu: KarSuFabsMenu) {
        fabsMenu.toggle()
    }

    open fun onMenuExpanded(fabsMenu: KarSuFabsMenu) {}

    open fun onMenuCollapsed(fabsMenu: KarSuFabsMenu) {}
}
