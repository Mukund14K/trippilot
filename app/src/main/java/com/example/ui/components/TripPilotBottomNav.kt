package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScreenState
import com.example.ui.theme.*

data class NavItem(
    val screen: ScreenState,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun TripPilotBottomNav(
    currentScreen: ScreenState,
    onNavigate: (ScreenState) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavItem(ScreenState.LANDING, "Explore", Icons.Filled.CompassCalibration, Icons.Outlined.CompassCalibration),
        NavItem(ScreenState.PLANNER, "Plan", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome),
        NavItem(ScreenState.DASHBOARD, "Itinerary", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
        NavItem(ScreenState.MAP, "Route", Icons.Filled.Map, Icons.Outlined.Map),
        NavItem(ScreenState.BUDGET, "Budget", Icons.Filled.AccountBalanceWallet, Icons.Outlined.AccountBalanceWallet),
        NavItem(ScreenState.SAVED, "Saved", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder)
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = CanvasBackground,
        shadowElevation = 8.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(HairpinBorder)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentScreen == item.screen
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(bounded = true, color = BrandForestLight)
                            ) {
                                onNavigate(item.screen)
                            }
                            .testTag("bottom_nav_${item.label.lowercase()}")
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) SurfaceSand else androidx.compose.ui.graphics.Color.Transparent)
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.label,
                                tint = if (isSelected) BrandForest else TextMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) BrandForestDark else TextMuted
                        )
                    }
                }
            }
        }
    }
}
