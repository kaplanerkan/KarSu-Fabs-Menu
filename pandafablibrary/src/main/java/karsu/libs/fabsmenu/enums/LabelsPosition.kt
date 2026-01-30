package karsu.libs.fabsmenu.enums

enum class LabelsPosition {
    LEFT, RIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): LabelsPosition = entries.getOrElse(ordinal) { LEFT }
    }
}
