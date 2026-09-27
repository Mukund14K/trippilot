package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.ActivityItem
import com.example.model.BudgetBreakdown
import com.example.model.DayItinerary
import com.example.model.ScreenState
import com.example.ui.theme.*

@Composable
fun DashboardItineraryScreen(
    destination: String,
    days: List<DayItinerary>,
    selectedDayIndex: Int,
    budget: BudgetBreakdown,
    onSelectDay: (Int) -> Unit,
    onOpenPlaceDetail: (ActivityItem) -> Unit,
    onSwapActivity: (Int, String) -> Unit,
    onDeleteActivity: (Int, String) -> Unit,
    onOpenAiAssistant: () -> Unit,
    onNavigate: (ScreenState) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeDay = days.getOrNull(selectedDayIndex) ?: days.first()
    val spentFraction = (budget.totalSpent.toFloat() / budget.totalTarget).coerceIn(0f, 1f)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = CanvasBackground,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onOpenAiAssistant,
                containerColor = BrandForest,
                contentColor = CanvasBackground,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .testTag("fab_refine_ai")
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = AccentTerracotta,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Refine with TripPilot",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Master Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceWarm)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$destination · 5 Days · 3 Travellers",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Oct 12 - Oct 17, 2026 · Clustered Proximity Route",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    // Total estimated cost badge
                    Surface(
                        color = SurfaceSand,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                        modifier = Modifier.clickable { onNavigate(ScreenState.BUDGET) }
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "EST. EXPENSE",
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentTerracotta
                            )
                            Text(
                                text = "₹${"%,d".format(budget.totalSpent)} / ₹${"%,d".format(budget.totalTarget)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = BrandForestDark
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Progress Bar for Budget
                LinearProgressIndicator(
                    progress = { spentFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = if (spentFraction > 0.95f) AccentTerracotta else BrandForest,
                    trackColor = HairpinBorder
                )
            }

            // View Switcher Tabs (Overview, Timeline, Map Route, Budget)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CanvasBackground)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = BrandForest,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Timeline",
                        color = CanvasBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.5.sp,
                        modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                Surface(
                    onClick = { onNavigate(ScreenState.MAP) },
                    color = SurfaceSand,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Map Route",
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.5.sp,
                        modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }

                Surface(
                    onClick = { onNavigate(ScreenState.BUDGET) },
                    color = SurfaceSand,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Budget",
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.5.sp,
                        modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Sticky Day Selector (Horizontal tabs)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                days.forEachIndexed { index, day ->
                    val isSelected = index == selectedDayIndex
                    Surface(
                        onClick = { onSelectDay(index) },
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) SurfaceSand else CanvasBackground,
                        border = androidx.compose.foundation.BorderStroke(
                            if (isSelected) 2.dp else 1.dp,
                            if (isSelected) BrandForest else HairpinBorder
                        ),
                        modifier = Modifier.testTag("day_tab_$index")
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "DAY ${day.dayNumber}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) AccentTerracotta else TextMuted
                            )
                            Text(
                                text = day.title,
                                fontFamily = FontFamily.Serif,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.5.sp,
                                color = TextPrimary,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

            // Subtitle banner for selected day
            Surface(
                color = SurfaceWarm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = AccentTerracotta,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = activeDay.subtitle,
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 16.sp
                    )
                }
            }

            // Chronological Activity Timeline
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 100.dp)
            ) {
                itemsIndexed(activeDay.activities) { idx, activity ->
                    ActivityTimelineCard(
                        activity = activity,
                        onOpenDetail = { onOpenPlaceDetail(activity) },
                        onSwap = { onSwapActivity(selectedDayIndex, activity.id) },
                        onDelete = { onDeleteActivity(selectedDayIndex, activity.id) }
                    )

                    // Transit Connector Block
                    if (activity.transitToNext != null) {
                        TransitConnector(transit = activity.transitToNext)
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityTimelineCard(
    activity: ActivityItem,
    onOpenDetail: () -> Unit,
    onSwap: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        onClick = onOpenDetail,
        shape = RoundedCornerShape(16.dp),
        color = SurfaceWarm,
        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("activity_card_${activity.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Timestamp and category header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(BrandForest)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${activity.time} · ${activity.duration}",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandForestDark
                    )
                }

                Surface(
                    color = SurfaceSand,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                ) {
                    Text(
                        text = activity.categoryTag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentTerracotta,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Photo Thumbnail + Title & Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AsyncImage(
                        model = activity.imageUrl,
                        contentDescription = activity.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = activity.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextPrimary,
                        lineHeight = 19.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = activity.locationName,
                            fontSize = 12.sp,
                            color = TextMuted,
                            maxLines = 1
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = if (activity.costInr > 0) "Est. ₹${activity.costInr}" else "Free Access",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandForestDark
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action controls (Swap, Delete, View Details)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onSwap,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Swap",
                        tint = AccentTerracotta,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Swap",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentTerracotta
                    )
                }

                TextButton(
                    onClick = onDelete,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = TextMuted,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Remove",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun TransitConnector(transit: com.example.model.TransitInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(12.dp)
                    .background(HairpinBorder)
            )
            Icon(
                imageVector = if (transit.mode == "walk") Icons.Default.DirectionsWalk else Icons.Default.DirectionsCar,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(14.dp)
            )
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(12.dp)
                    .background(HairpinBorder)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Surface(
            color = SurfaceSand,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${transit.durationText} (${transit.distanceText})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrandForestDark
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "· ${transit.routeNote}",
                    fontSize = 10.5.sp,
                    color = TextMuted,
                    maxLines = 1
                )
            }
        }
    }
}
