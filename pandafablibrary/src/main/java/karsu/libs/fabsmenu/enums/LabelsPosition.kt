package karsu.libs.fabsmenu.enums

/**
 * FAB butonlarının etiket (label) konumunu belirleyen enum sınıfı.
 *
 * Bu enum, KarsuTitleFab bileşenlerinin yanında görünen metin etiketlerinin
 * FAB butonuna göre hangi tarafta konumlandırılacağını tanımlar.
 *
 * Kullanılabilir konumlar:
 * - [LEFT]: Etiket FAB butonunun solunda görünür (varsayılan, sağ alt köşedeki menüler için ideal)
 * - [RIGHT]: Etiket FAB butonunun sağında görünür (sol alt köşedeki menüler için ideal)
 *
 * NOT: Bu ayar sadece UP ve DOWN açılma yönlerinde geçerlidir.
 * LEFT ve RIGHT yönlerinde etiketler gösterilmez.
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 */
enum class LabelsPosition {
    LEFT, RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): LabelsPosition = entries.getOrElse(ordinal) { LEFT }
    }
}
