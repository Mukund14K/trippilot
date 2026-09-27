package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class GenerationStep(
    val title: String,
    val isComplete: Boolean,
    val inProgress: Boolean
)

data class ToastMessage(
    val id: Long = System.currentTimeMillis(),
    val message: String
)

data class TripPilotUiState(
    val currentScreen: ScreenState = ScreenState.LANDING,
    val days: List<DayItinerary> = SampleData.initialDays,
    val selectedDayIndex: Int = 0, // 0 to 4 (Day 1 to 5)
    val budget: BudgetBreakdown = BudgetBreakdown(),
    val suggestions: List<OptimizationSuggestion> = SampleData.initialSuggestions,
    val plannerForm: PlannerForm = PlannerForm(),
    val plannerCurrentStep: Int = 1, // 1 to 7
    val generationProgress: Float = 0f,
    val generationSteps: List<GenerationStep> = listOf(
        GenerationStep("Understanding your travel style & group dynamics", false, true),
        GenerationStep("Filtering destinations & local spots worth visiting", false, false),
        GenerationStep("Clustering nearby venues to eliminate travel dead-time", false, false),
        GenerationStep("Balancing estimated expenses against budget target", false, false),
        GenerationStep("Sequencing days for golden-hour views & optimal opening hours", false, false)
    ),
    val selectedPlaceForDetail: ActivityItem? = null,
    val selectedMapMarkerId: String? = "act-1-1",
    val mapDayFilter: Int = 1, // 0 = Full route, 1..5 = Day specific
    val isAiAssistantOpen: Boolean = false,
    val activeToast: ToastMessage? = null,
    val savedTripsTab: String = "Upcoming" // "Upcoming", "Drafts", "Completed"
)

class TripPilotViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TripPilotUiState())
    val uiState: StateFlow<TripPilotUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: ScreenState) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDayIndex = index) }
    }

    fun setMapDayFilter(day: Int) {
        _uiState.update { it.copy(mapDayFilter = day) }
    }

    fun selectMapMarker(activityId: String) {
        _uiState.update { it.copy(selectedMapMarkerId = activityId) }
    }

    fun openPlaceDetail(item: ActivityItem) {
        _uiState.update { it.copy(selectedPlaceForDetail = item) }
    }

    fun closePlaceDetail() {
        _uiState.update { it.copy(selectedPlaceForDetail = null) }
    }

    fun toggleAiAssistant(open: Boolean? = null) {
        _uiState.update {
            it.copy(isAiAssistantOpen = open ?: !it.isAiAssistantOpen)
        }
    }

    fun setSavedTripsTab(tab: String) {
        _uiState.update { it.copy(savedTripsTab = tab) }
    }

    // Planner updates
    fun updatePlannerDestination(dest: String) {
        _uiState.update { it.copy(plannerForm = it.plannerForm.copy(destination = dest)) }
    }

    fun updatePlannerDates(dates: String) {
        _uiState.update { it.copy(plannerForm = it.plannerForm.copy(dateRange = dates)) }
    }

    fun updateTravellers(adults: Int, children: Int) {
        _uiState.update {
            it.copy(
                plannerForm = it.plannerForm.copy(
                    adults = adults.coerceAtLeast(1),
                    children = children.coerceAtLeast(0)
                )
            )
        }
    }

    fun updateBudget(budgetInr: Int) {
        _uiState.update {
            it.copy(
                plannerForm = it.plannerForm.copy(budgetInr = budgetInr),
                budget = it.budget.copy(totalTarget = budgetInr)
            )
        }
    }

    fun toggleInterest(interest: String) {
        _uiState.update { state ->
            val set = state.plannerForm.selectedInterests.toMutableSet()
            if (set.contains(interest)) {
                if (set.size > 1) set.remove(interest)
            } else {
                set.add(interest)
            }
            state.copy(plannerForm = state.plannerForm.copy(selectedInterests = set))
        }
    }

    fun updatePace(pace: String) {
        _uiState.update { it.copy(plannerForm = it.plannerForm.copy(pace = pace)) }
    }

    fun updateStyle(style: String) {
        _uiState.update { it.copy(plannerForm = it.plannerForm.copy(travelStyle = style)) }
    }

    fun setPlannerStep(step: Int) {
        _uiState.update { it.copy(plannerCurrentStep = step.coerceIn(1, 7)) }
    }

    fun nextPlannerStep() {
        if (_uiState.value.plannerCurrentStep < 7) {
            _uiState.update { it.copy(plannerCurrentStep = it.plannerCurrentStep + 1) }
        } else {
            startTripGeneration()
        }
    }

    fun prevPlannerStep() {
        if (_uiState.value.plannerCurrentStep > 1) {
            _uiState.update { it.copy(plannerCurrentStep = it.plannerCurrentStep - 1) }
        } else {
            navigateTo(ScreenState.LANDING)
        }
    }

    fun prefillAndPlan(destName: String, budget: Int = 20000) {
        _uiState.update {
            it.copy(
                plannerForm = it.plannerForm.copy(
                    destination = destName,
                    budgetInr = budget
                ),
                plannerCurrentStep = 1,
                currentScreen = ScreenState.PLANNER
            )
        }
    }

    fun startTripGeneration() {
        _uiState.update {
            it.copy(
                currentScreen = ScreenState.GENERATING,
                generationProgress = 0.05f,
                generationSteps = listOf(
                    GenerationStep("Understanding your travel style & group dynamics", false, true),
                    GenerationStep("Filtering destinations & local spots worth visiting", false, false),
                    GenerationStep("Clustering nearby venues to eliminate travel dead-time", false, false),
                    GenerationStep("Balancing estimated expenses against ₹${it.plannerForm.budgetInr} budget", false, false),
                    GenerationStep("Sequencing days for golden-hour views & optimal opening hours", false, false)
                )
            )
        }

        viewModelScope.launch {
            for (stepIndex in 0 until 5) {
                delay(750)
                _uiState.update { state ->
                    val updatedSteps = state.generationSteps.mapIndexed { idx, step ->
                        when {
                            idx < stepIndex -> GenerationStep(step.title, isComplete = true, inProgress = false)
                            idx == stepIndex -> GenerationStep(step.title, isComplete = true, inProgress = false)
                            idx == stepIndex + 1 -> GenerationStep(step.title, isComplete = false, inProgress = true)
                            else -> GenerationStep(step.title, isComplete = false, inProgress = false)
                        }
                    }
                    state.copy(
                        generationProgress = (stepIndex + 1) * 0.20f,
                        generationSteps = updatedSteps
                    )
                }
            }
            delay(500)
            _uiState.update { it.copy(currentScreen = ScreenState.DASHBOARD) }
            showToast("✨ Itinerary generated & optimized for ${uiState.value.plannerForm.destination}!")
        }
    }

    fun skipGeneration() {
        _uiState.update { it.copy(currentScreen = ScreenState.DASHBOARD) }
        showToast("Loaded optimized itinerary.")
    }

    // Itinerary actions
    fun deleteActivity(dayIndex: Int, activityId: String) {
        _uiState.update { state ->
            val updatedDays = state.days.mapIndexed { idx, day ->
                if (idx == dayIndex) {
                    val filtered = day.activities.filter { it.id != activityId }
                    day.copy(activities = filtered)
                } else day
            }
            state.copy(days = updatedDays)
        }
        showToast("Activity removed from Day ${dayIndex + 1}")
    }

    fun swapActivity(dayIndex: Int, activityId: String) {
        val alternatives = listOf(
            ActivityItem(
                id = "alt-" + System.currentTimeMillis(),
                time = "03:00 PM",
                duration = "1h 45m",
                title = "Reis Magos Fort Coastal Overlook",
                locationName = "Reis Magos, Verem",
                costInr = 120,
                categoryTag = "Heritage & Views",
                imageUrl = "https://images.unsplash.com/photo-1590073844006-33379778ae09?w=800&auto=format&fit=crop&q=80",
                transitToNext = TransitInfo("15 min drive (6 km)", "6 km", "via Verem road", "drive"),
                whyVisit = "Restored 1551 fortress overlooking the Mandovi estuary with fewer crowds and sea breezes.",
                bestTime = "03:30 PM - 05:00 PM",
                entryFee = "₹50/person",
                nearbyGems = listOf("Verem Jetty", "Coco Beach"),
                lat = 15.4980f,
                lng = 73.8080f
            ),
            ActivityItem(
                id = "alt-" + System.currentTimeMillis(),
                time = "02:00 PM",
                duration = "2h 00m",
                title = "Mum's Kitchen Traditional Goan Tasting",
                locationName = "Panaji Waterfront",
                costInr = 1100,
                categoryTag = "Culinary",
                imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800&auto=format&fit=crop&q=80",
                transitToNext = TransitInfo("10 min drive (3 km)", "3 km", "via DB Marg", "drive"),
                whyVisit = "Dedicated to reviving heirloom recipes from Hindu and Christian Goan ancestral kitchens.",
                bestTime = "02:00 PM",
                entryFee = "A la carte menu",
                nearbyGems = listOf("Miramar Beach", "Kala Academy"),
                lat = 15.4920f,
                lng = 73.8150f
            )
        )

        val replacement = alternatives.random()
        _uiState.update { state ->
            val updatedDays = state.days.mapIndexed { idx, day ->
                if (idx == dayIndex) {
                    val newActivities = day.activities.map { act ->
                        if (act.id == activityId) replacement.copy(time = act.time) else act
                    }
                    day.copy(activities = newActivities)
                } else day
            }
            state.copy(days = updatedDays)
        }
        showToast("Swapped with '${replacement.title}'")
    }

    // Budget optimization suggestions
    fun applySuggestion(suggestionId: String) {
        _uiState.update { state ->
            val updatedList = state.suggestions.map { item ->
                if (item.id == suggestionId) item.copy(isApplied = !item.isApplied) else item
            }
            val changedItem = updatedList.firstOrNull { it.id == suggestionId }
            val isNowApplied = changedItem?.isApplied == true
            val diff = if (isNowApplied) -(changedItem?.savingsInr ?: 0) else (changedItem?.savingsInr ?: 0)

            val updatedBudget = when (changedItem?.category) {
                "Transport" -> state.budget.copy(transport = state.budget.transport + diff)
                "Activities" -> state.budget.copy(activities = state.budget.activities + diff)
                "Food" -> state.budget.copy(food = state.budget.food + diff)
                else -> state.budget.copy(stay = state.budget.stay + diff)
            }

            state.copy(
                suggestions = updatedList,
                budget = updatedBudget
            )
        }
        val item = _uiState.value.suggestions.firstOrNull { it.id == suggestionId }
        if (item?.isApplied == true) {
            showToast("Saved ₹${item.savingsInr}! Budget updated.")
        } else {
            showToast("Reverted suggestion.")
        }
    }

    // Natural-language AI Itinerary Modifier
    fun applyAiPrompt(prompt: String) {
        val lower = prompt.lowercase()
        when {
            lower.contains("relaxed") || lower.contains("pace") -> {
                _uiState.update { state ->
                    val day2 = state.days.getOrNull(1)
                    if (day2 != null) {
                        val relaxedActivities = day2.activities.take(3).mapIndexed { i, a ->
                            val shiftedTime = when (i) {
                                0 -> "10:30 AM"
                                1 -> "01:30 PM"
                                else -> "05:00 PM"
                            }
                            a.copy(time = shiftedTime, duration = "2h 30m")
                        }
                        val newDays = state.days.toMutableList()
                        newDays[1] = day2.copy(
                            subtitle = "Relaxed pace: Morning sleep-in, leisurely cafe brunch & sunset",
                            activities = relaxedActivities
                        )
                        state.copy(days = newDays)
                    } else state
                }
                showToast("Pushed Day 2 start to 10:30 AM and gave each stop 2.5 hours.")
            }
            lower.contains("seafood") || lower.contains("lunch") || lower.contains("food") -> {
                _uiState.update { state ->
                    val day1 = state.days[0]
                    val updatedActs = day1.activities.map { act ->
                        if (act.id == "act-1-3") {
                            act.copy(
                                title = "Vinayak Family Restaurant (Authentic Seafood Thali)",
                                costInr = 850,
                                whyVisit = "Hidden local hotspot famous for crisp rava-fried Chonak fish and crab masala thali."
                            )
                        } else act
                    }
                    val newDays = state.days.toMutableList()
                    newDays[0] = day1.copy(activities = updatedActs)
                    state.copy(days = newDays)
                }
                showToast("Added authentic local Goan seafood thali spot in Day 1!")
            }
            lower.contains("sunset") || lower.contains("crowd") -> {
                _uiState.update { state ->
                    val day1 = state.days[0]
                    val updatedActs = day1.activities.map { act ->
                        if (act.id == "act-1-4") {
                            act.copy(
                                title = "Sinquerim Secret Cove Sunset Deck",
                                locationName = "Sinquerim Cliff Path",
                                whyVisit = "Hidden lateralite cliff trail 400m past the main fort with 90% fewer visitors."
                            )
                        } else act
                    }
                    val newDays = state.days.toMutableList()
                    newDays[0] = day1.copy(activities = updatedActs)
                    state.copy(days = newDays)
                }
                showToast("Swapped to secluded secret cove sunset deck!")
            }
            lower.contains("cut") || lower.contains("budget") || lower.contains("save") || lower.contains("1,000") -> {
                _uiState.update { state ->
                    val newBudget = state.budget.copy(
                        activities = (state.budget.activities - 600).coerceAtLeast(1000),
                        food = (state.budget.food - 400).coerceAtLeast(1000)
                    )
                    state.copy(budget = newBudget)
                }
                showToast("Optimized Day 3 dining & activities: Reduced ₹1,000!")
            }
            else -> {
                // Generic custom modification
                _uiState.update { state ->
                    val curDay = state.selectedDayIndex
                    val day = state.days[curDay]
                    val shiftedActivities = day.activities.mapIndexed { idx, act ->
                        val newTime = when (idx) {
                            0 -> "10:15 AM"
                            1 -> "01:00 PM"
                            2 -> "03:45 PM"
                            else -> "06:15 PM"
                        }
                        act.copy(time = newTime)
                    }
                    val newDays = state.days.toMutableList()
                    newDays[curDay] = day.copy(activities = shiftedActivities)
                    state.copy(days = newDays)
                }
                showToast("TripPilot updated Day ${uiState.value.selectedDayIndex + 1} per your instruction.")
            }
        }
        toggleAiAssistant(false)
    }

    private fun showToast(msg: String) {
        _uiState.update { it.copy(activeToast = ToastMessage(message = msg)) }
    }

    fun dismissToast() {
        _uiState.update { it.copy(activeToast = null) }
    }
}
