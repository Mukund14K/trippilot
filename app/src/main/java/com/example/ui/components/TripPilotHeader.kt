package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenState
import com.example.ui.theme.*

@Composable
fun TripPilotHeader(
    currentScreen: ScreenState,
    onNavigate: (ScreenState) -> Unit,
    onPlanTripClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CanvasBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Brand Logo & Wordmark
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onNavigate(ScreenState.LANDING) }
                    .padding(vertical = 4.dp, horizontal = 6.dp)
                    .testTag("brand_logo_button")
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BrandForest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "TripPilot Logo",
                        tint = AccentTerracotta,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "TripPilot",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        color = BrandForestDark,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "TRAVEL OPTIMIZER",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 8.5.sp,
                        color = AccentTerracotta,
                        letterSpacing = 1.2.sp
                    )
                }
            }

            // Primary Top CTA
            Button(
                onClick = onPlanTripClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandForest,
                    contentColor = CanvasBackground
                ),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier
                    .height(38.dp)
                    .testTag("plan_a_trip_button")
            ) {
                Icon(
                    imageVector = Icons.Default.AddLocationAlt,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = AccentTerracotta
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Plan a Trip",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.5.sp
                )
            }
        }

        // Horizontal Quick-Jump Screen Strip (Tactile tabs)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val tabs = listOf(
                ScreenState.LANDING to "Overview",
                ScreenState.DASHBOARD to "Goa Itinerary",
                ScreenState.MAP to "Route Map",
                ScreenState.BUDGET to "Budget Health",
                ScreenState.DISCOVERY to "Explore Destinations",
                ScreenState.SAVED to "Saved Trips",
                ScreenState.PLANNER to "Trip Wizard"
            )

            tabs.forEach { (state, label) ->
                val isSelected = currentScreen == state
                Surface(
                    onClick = { onNavigate(state) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) BrandForest else SurfaceSand,
                    modifier = Modifier
                        .height(30.dp)
                        .testTag("nav_tab_${state.name.lowercase()}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(AccentTerracotta)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(
                            text = label,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) CanvasBackground else TextPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(HairpinBorder)
        )
    }
}
