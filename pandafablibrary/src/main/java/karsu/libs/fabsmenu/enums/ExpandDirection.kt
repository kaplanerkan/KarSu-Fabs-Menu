package karsu.libs.fabsmenu.enums

/**
 * FAB menüsünün açılma yönünü belirleyen enum sınıfı.
 *
 * Bu enum, KarSuFabsMenu bileşeninin hangi yöne doğru genişleyeceğini tanımlar.
 * Menü butonuna tıklandığında, alt FAB butonları bu yönde animasyonlu olarak açılır.
 *
 * Kullanılabilir yönler:
 * - [UP]: Yukarı doğru açılır (varsayılan, ekranın alt kısmındaki menüler için ideal)
 * - [DOWN]: Aşağı doğru açılır (ekranın üst kısmındaki menüler için ideal)
 * - [LEFT]: Sola doğru açılır (ekranın sağ kısmındaki menüler için ideal)
 * - [RIGHT]: Sağa doğru açılır (ekranın sol kısmındaki menüler için ideal)
 *
 * NOT: LEFT ve RIGHT yönlerinde etiketler (labels) gösterilmez.
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 */
enum class ExpandDirection {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): ExpandDirection = entries.getOrElse(ordinal) { UP }
    }
}
