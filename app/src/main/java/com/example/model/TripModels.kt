package com.example.model

enum class ScreenState {
    LANDING,
    PLANNER,
    GENERATING,
    DASHBOARD,
    MAP,
    BUDGET,
    DISCOVERY,
    SAVED
}

data class TransitInfo(
    val durationText: String,
    val distanceText: String,
    val routeNote: String,
    val mode: String = "drive" // drive, walk, ferry
)

data class ActivityItem(
    val id: String,
    val time: String,
    val duration: String,
    val title: String,
    val locationName: String,
    val costInr: Int,
    val categoryTag: String,
    val imageUrl: String,
    val transitToNext: TransitInfo? = null,
    val whyVisit: String,
    val bestTime: String,
    val entryFee: String,
    val nearbyGems: List<String>,
    val lat: Float,
    val lng: Float
)

data class DayItinerary(
    val dayNumber: Int,
    val title: String,
    val subtitle: String,
    val activities: List<ActivityItem>
)

data class BudgetCategory(
    val name: String,
    val allocatedInr: Int,
    val iconName: String,
    val colorHex: Long
)

data class BudgetBreakdown(
    val transport: Int = 5200,
    val stay: Int = 8000,
    val food: Int = 4800,
    val activities: Int = 3200,
    val buffer: Int = 2280,
    val totalTarget: Int = 25000
) {
    val totalSpent: Int get() = transport + stay + food + activities
    val remainingBalance: Int get() = totalTarget - totalSpent
    val dailyAverage: Int get() = totalSpent / 5
}

data class OptimizationSuggestion(
    val id: String,
    val title: String,
    val description: String,
    val savingsInr: Int,
    val category: String,
    val isApplied: Boolean = false
)

data class DestinationHighlight(
    val id: String,
    val name: String,
    val state: String,
    val tagline: String,
    val bucket: String, // "Under ₹10k", "Culinary", "Sanctuary"
    val bestSeason: String,
    val budgetEstimate: String,
    val travelTime: String,
    val tags: List<String>,
    val imageUrl: String,
    val description: String
)

data class SavedTripItem(
    val id: String,
    val title: String,
    val destination: String,
    val dates: String,
    val companions: String,
    val spentInr: Int,
    val targetInr: Int,
    val status: String, // "Upcoming", "Drafts", "Completed"
    val imageUrl: String
)

data class PlannerForm(
    val destination: String = "Goa",
    val dateRange: String = "Oct 12 - Oct 17 (5 Days)",
    val adults: Int = 2,
    val children: Int = 1,
    val budgetInr: Int = 25000,
    val selectedInterests: Set<String> = setOf("Beaches", "Food", "Heritage"),
    val pace: String = "Balanced", // Relaxed, Balanced, Packed
    val travelStyle: String = "Balanced" // Budget, Balanced, Premium
)
