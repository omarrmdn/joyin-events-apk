package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val hostName: String,
    val location: String,
    val dateTime: String,
    val price: Double,
    val description: String,
    val tags: String, // Comma separated tags
    val audience: String, // e.g. "Males Only", "Everyone"
    val attendingCount: Int = 0,
    val dateDay: String, // e.g. "6"
    val dateWeekday: String, // e.g. "Fri"
    val isJoined: Boolean = false,
    val imageUrl: String? = "cream",
    val attendeeNames: String = "", // Comma-separated list of attendee names
    val currencyCode: String = "EGP",
    val maxCapacity: Int = 100
)

fun Event.formattedPrice(): String {
    if (price == 0.0) return "Free"
    val curr = currencyCode.trim()
    val formattedPrice = if (price % 1.0 == 0.0) {
        String.format(java.util.Locale.US, "%.0f", price)
    } else {
        String.format(java.util.Locale.US, "%.2f", price)
    }
    return if (curr.uppercase() == "USD") {
        "$$formattedPrice"
    } else {
        "$formattedPrice ${curr.uppercase()}"
    }
}
