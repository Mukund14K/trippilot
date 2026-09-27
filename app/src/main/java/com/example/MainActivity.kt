package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.ScreenState
import com.example.ui.components.AiAssistantModal
import com.example.ui.components.PlaceDetailSheet
import com.example.ui.components.TripPilotBottomNav
import com.example.ui.components.TripPilotHeader
import com.example.ui.screens.*
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.TripPilotViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TripPilotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                TripPilotApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TripPilotApp(viewModel: TripPilotViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Toast feedback handler
    LaunchedEffect(uiState.activeToast) {
        uiState.activeToast?.let { toast ->
            snackbarHostState.showSnackbar(
                message = toast.message,
                duration = SnackbarDuration.Short
            )
            viewModel.dismissToast()
        }
    }

    // Android Hardware Back Button Handling
    BackHandler {
        when {
            uiState.selectedPlaceForDetail != null -> {
                viewModel.closePlaceDetail()
            }
            uiState.isAiAssistantOpen -> {
                viewModel.toggleAiAssistant(false)
            }
            uiState.currentScreen == ScreenState.PLANNER -> {
                viewModel.prevPlannerStep()
            }
            uiState.currentScreen != ScreenState.LANDING -> {
                viewModel.navigateTo(ScreenState.LANDING)
            }
            else -> {
                // Let system exit app
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CanvasBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (uiState.currentScreen != ScreenState.GENERATING) {
                TripPilotHeader(
                    currentScreen = uiState.currentScreen,
                    onNavigate = { viewModel.navigateTo(it) },
                    onPlanTripClick = {
                        viewModel.setPlannerStep(1)
                        viewModel.navigateTo(ScreenState.PLANNER)
                    }
                )
            }
        },
        bottomBar = {
            if (uiState.currentScreen != ScreenState.GENERATING && uiState.currentScreen != ScreenState.PLANNER) {
                TripPilotBottomNav(
                    currentScreen = uiState.currentScreen,
                    onNavigate = { viewModel.navigateTo(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CanvasBackground)
        ) {
            when (uiState.currentScreen) {
                ScreenState.LANDING -> {
                    LandingScreen(
                        onPlanTripClick = {
                            viewModel.setPlannerStep(1)
                            viewModel.navigateTo(ScreenState.PLANNER)
                        },
                        onExploreClick = {
                            viewModel.navigateTo(ScreenState.DISCOVERY)
                        },
                        onDestinationSelect = { destName, budget ->
                            viewModel.prefillAndPlan(destName, budget)
                        },
                        onQuickPlanSubmit = { dest, dates, budget, travellers ->
                            viewModel.updatePlannerDestination(dest)
                            viewModel.updatePlannerDates(dates)
                            viewModel.updateBudget(budget)
                            viewModel.updateTravellers(travellers - 1, 1)
                            viewModel.startTripGeneration()
                        }
                    )
                }

                ScreenState.PLANNER -> {
                    PlannerScreen(
                        currentStep = uiState.plannerCurrentStep,
                        form = uiState.plannerForm,
                        onDestinationChange = { viewModel.updatePlannerDestination(it) },
                        onDatesChange = { viewModel.updatePlannerDates(it) },
                        onTravellersChange = { a, c -> viewModel.updateTravellers(a, c) },
                        onBudgetChange = { viewModel.updateBudget(it) },
                        onToggleInterest = { viewModel.toggleInterest(it) },
                        onPaceChange = { viewModel.updatePace(it) },
                        onStyleChange = { viewModel.updateStyle(it) },
                        onNextStep = { viewModel.nextPlannerStep() },
                        onPrevStep = { viewModel.prevPlannerStep() }
                    )
                }

                ScreenState.GENERATING -> {
                    GeneratingScreen(
                        destination = uiState.plannerForm.destination,
                        budget = uiState.plannerForm.budgetInr,
                        progress = uiState.generationProgress,
                        steps = uiState.generationSteps,
                        onSkip = { viewModel.skipGeneration() }
                    )
                }

                ScreenState.DASHBOARD -> {
                    DashboardItineraryScreen(
                        destination = uiState.plannerForm.destination,
                        days = uiState.days,
                        selectedDayIndex = uiState.selectedDayIndex,
                        budget = uiState.budget,
                        onSelectDay = { viewModel.selectDay(it) },
                        onOpenPlaceDetail = { viewModel.openPlaceDetail(it) },
                        onSwapActivity = { dayIdx, actId -> viewModel.swapActivity(dayIdx, actId) },
                        onDeleteActivity = { dayIdx, actId -> viewModel.deleteActivity(dayIdx, actId) },
                        onOpenAiAssistant = { viewModel.toggleAiAssistant(true) },
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                ScreenState.MAP -> {
                    RouteMapScreen(
                        days = uiState.days,
                        dayFilter = uiState.mapDayFilter,
                        selectedMarkerId = uiState.selectedMapMarkerId,
                        onSelectDayFilter = { viewModel.setMapDayFilter(it) },
                        onSelectMarker = { viewModel.selectMapMarker(it) },
                        onOpenPlaceDetail = { viewModel.openPlaceDetail(it) }
                    )
                }

                ScreenState.BUDGET -> {
                    BudgetHealthScreen(
                        budget = uiState.budget,
                        suggestions = uiState.suggestions,
                        onApplySuggestion = { viewModel.applySuggestion(it) }
                    )
                }

                ScreenState.DISCOVERY -> {
                    DiscoveryScreen(
                        onPlanDestination = { destName, budget ->
                            viewModel.prefillAndPlan(destName, budget)
                        }
                    )
                }

                ScreenState.SAVED -> {
                    SavedTripsScreen(
                        activeTab = uiState.savedTripsTab,
                        onTabSelect = { viewModel.setSavedTripsTab(it) },
                        onResumeTrip = {
                            viewModel.navigateTo(ScreenState.DASHBOARD)
                        },
                        onShareTrip = { tripTitle ->
                            Toast.makeText(context, "Share link for '$tripTitle' copied!", Toast.LENGTH_SHORT).show()
                        },
                        onDuplicateTrip = { tripTitle ->
                            Toast.makeText(context, "Duplicated '$tripTitle' to Drafts", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            // Place Details Modal Bottom Sheet
            PlaceDetailSheet(
                item = uiState.selectedPlaceForDetail,
                onDismiss = { viewModel.closePlaceDetail() },
                onSwap = { act ->
                    viewModel.swapActivity(uiState.selectedDayIndex, act.id)
                }
            )

            // AI Natural Language Assistant Modal Bottom Sheet
            AiAssistantModal(
                isOpen = uiState.isAiAssistantOpen,
                onDismiss = { viewModel.toggleAiAssistant(false) },
                onApplyPrompt = { prompt ->
                    viewModel.applyAiPrompt(prompt)
                }
            )
        }
    }
}
