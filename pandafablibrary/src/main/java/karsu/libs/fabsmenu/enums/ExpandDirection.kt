package karsu.libs.fabsmenu.enums

/**
 * Enum class that defines the expand direction of the FAB menu.
 *
 * This enum defines which direction the KarSuFabsMenu component will expand.
 * When the menu button is clicked, the child FAB buttons animate open in this direction.
 *
 * Available directions:
 * - [UP]: Expands upward (default, ideal for menus at the bottom of the screen)
 * - [DOWN]: Expands downward (ideal for menus at the top of the screen)
 * - [LEFT]: Expands to the left (ideal for menus on the right side of the screen)
 * - [RIGHT]: Expands to the right (ideal for menus on the left side of the screen)
 *
 * NOTE: Labels are not displayed in LEFT and RIGHT directions.
 *
 * @author Erkan Kaplan
 * @since 1.0.0
 * @date 2026-01-30
 */
enum class ExpandDirection {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): ExpandDirection = entries.getOrElse(ordinal) { UP }
    }
}
