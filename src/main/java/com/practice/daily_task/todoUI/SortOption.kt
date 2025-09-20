package com.practice.daily_task.todoUI

enum class SortOption(val label : String) {
    CREATED_LAST(("Created Last")),
    CREATED_FIRST("Created First"),
    DUE_EARLIEST_FIRST("Earliest First"),
    DUE_LATEST_FIRST("Latest First"),
    PRIORITY_HIGH_LOW("High → Low"),
    PRIORITY_LOW_HIGH("Low → High"),
    COMPLETE_FIRST("Complete First"),
    INCOMPLETE_FIRST("Incomplete First"),
    A_TO_Z("A → Z"),
    Z_TO_A("Z → A")
}