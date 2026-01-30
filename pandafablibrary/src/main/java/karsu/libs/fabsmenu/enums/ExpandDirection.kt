package karsu.libs.fabsmenu.enums

enum class ExpandDirection {
    UP, DOWN, LEFT, RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): ExpandDirection = entries.getOrElse(ordinal) { UP }
    }
}
