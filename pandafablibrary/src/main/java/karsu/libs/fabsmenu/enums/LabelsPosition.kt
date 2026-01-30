package karsu.libs.fabsmenu.enums

/**
 * Enum class that defines the label position of FAB buttons.
 *
 * This enum defines on which side of the FAB button the text labels
 * appearing next to KarsuTitleFab components will be positioned.
 *
 * Available positions:
 * - [LEFT]: Label appears on the left of the FAB button (default, ideal for bottom-right menus)
 * - [RIGHT]: Label appears on the right of the FAB button (ideal for bottom-left menus)
 *
 * NOTE: This setting only applies to UP and DOWN expand directions.
 * Labels are not displayed in LEFT and RIGHT directions.
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
enum class LabelsPosition {
    LEFT, RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): LabelsPosition = entries.getOrElse(ordinal) { LEFT }
    }
}
