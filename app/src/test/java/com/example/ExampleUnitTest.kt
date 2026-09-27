package com.example

import com.example.model.BudgetBreakdown
import com.example.model.ScreenState
import com.example.viewmodel.TripPilotViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testBudgetCalculation() {
    val budget = BudgetBreakdown(
      transport = 5200,
      stay = 8000,
      food = 4800,
      activities = 3200,
      buffer = 2280,
      totalTarget = 25000
    )
    assertEquals(21200, budget.totalSpent)
    assertEquals(3800, budget.remainingBalance)
    assertEquals(4240, budget.dailyAverage)
  }

  @Test
  fun testViewModelStepTransitions() {
    val viewModel = TripPilotViewModel()
    assertEquals(ScreenState.LANDING, viewModel.uiState.value.currentScreen)
    assertEquals(1, viewModel.uiState.value.plannerCurrentStep)

    viewModel.setPlannerStep(3)
    assertEquals(3, viewModel.uiState.value.plannerCurrentStep)

    viewModel.updatePlannerDestination("Jaipur")
    assertEquals("Jaipur", viewModel.uiState.value.plannerForm.destination)
  }

  @Test
  fun testOptimizationSuggestionApplication() {
    val viewModel = TripPilotViewModel()
    val initialTransport = viewModel.uiState.value.budget.transport
    viewModel.applySuggestion("opt-1")
    assertTrue(viewModel.uiState.value.suggestions.first { it.id == "opt-1" }.isApplied)
    assertEquals(initialTransport - 1400, viewModel.uiState.value.budget.transport)
  }
}
