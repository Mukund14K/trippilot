package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActivityItem
import com.example.model.DayItinerary
import com.example.ui.theme.*

@Composable
fun RouteMapScreen(
    days: List<DayItinerary>,
    dayFilter: Int, // 0 = Full Route, 1..5 = Day
    selectedMarkerId: String?,
    onSelectDayFilter: (Int) -> Unit,
    onSelectMarker: (String) -> Unit,
    onOpenPlaceDetail: (ActivityItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeActivities = remember(dayFilter, days) {
        if (dayFilter == 0) {
            days.flatMap { it.activities }
        } else {
            days.getOrNull(dayFilter - 1)?.activities ?: days.first().activities
        }
    }

    val selectedActivity = remember(selectedMarkerId, activeActivities) {
        activeActivities.firstOrNull { it.id == selectedMarkerId } ?: activeActivities.firstOrNull()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground)
            .padding(bottom = 70.dp)
    ) {
        // Map Top Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWarm)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Geographic Route Clustering",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = TextPrimary
            )
            Text(
                text = "Sequential travel path optimized to eliminate criss-crossing.",
                fontSize = 12.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Day Filter Strip (Full Route, Day 1, Day 2, Day 3, etc.)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    onClick = { onSelectDayFilter(0) },
                    shape = RoundedCornerShape(14.dp),
                    color = if (dayFilter == 0) BrandForest else SurfaceSand,
                    border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                ) {
                    Text(
                        text = "Full Route (5 Days)",
                        fontSize = 11.5.sp,
                        fontWeight = if (dayFilter == 0) FontWeight.Bold else FontWeight.Medium,
                        color = if (dayFilter == 0) CanvasBackground else TextPrimary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                days.forEachIndexed { idx, day ->
                    val dayNum = idx + 1
                    val isSelected = dayFilter == dayNum
                    Surface(
                        onClick = { onSelectDayFilter(dayNum) },
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) BrandForest else SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                    ) {
                        Text(
                            text = "Day $dayNum: ${day.title.take(12)}..",
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) CanvasBackground else TextPrimary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Custom Map Canvas Visual Representation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(Color(0xFFE8E5DC)) // Map parchment tone
        ) {
            // Draw route connections and coastline accents on Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Draw background cartographic grid
                val gridSpacing = 40.dp.toPx()
                var x = 0f
                while (x < canvasWidth) {
                    drawLine(
                        color = Color(0x221B4332),
                        start = Offset(x, 0f),
                        end = Offset(x, canvasHeight),
                        strokeWidth = 1f
                    )
                    x += gridSpacing
                }

                var y = 0f
                while (y < canvasHeight) {
                    drawLine(
                        color = Color(0x221B4332),
                        start = Offset(0f, y),
                        end = Offset(canvasWidth, y),
                        strokeWidth = 1f
                    )
                    y += gridSpacing
                }

                // Coastline curves (Stylized Goa Arabian sea boundary)
                drawCircle(
                    color = Color(0x181B4332),
                    radius = 280.dp.toPx(),
                    center = Offset(-60.dp.toPx(), canvasHeight / 2)
                )

                // Normalized node coordinates for activities
                val count = activeActivities.size
                if (count > 1) {
                    val points = activeActivities.mapIndexed { index, _ ->
                        val fx = 0.18f + (index.toFloat() / (count - 1).coerceAtLeast(1)) * 0.64f
                        val fy = when (index % 4) {
                            0 -> 0.35f
                            1 -> 0.65f
                            2 -> 0.45f
                            else -> 0.75f
                        }
                        Offset(fx * canvasWidth, fy * canvasHeight)
                    }

                    // Draw connecting path line
                    for (i in 0 until points.size - 1) {
                        drawLine(
                            color = Color(0xFF1B4332),
                            start = points[i],
                            end = points[i + 1],
                            strokeWidth = 3.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
                        )
                    }
                }
            }

            // Interactive Waypoint Nodes overlay
            activeActivities.forEachIndexed { index, act ->
                val count = activeActivities.size
                val fx = 0.18f + (index.toFloat() / (count - 1).coerceAtLeast(1)) * 0.64f
                val fy = when (index % 4) {
                    0 -> 0.35f
                    1 -> 0.65f
                    2 -> 0.45f
                    else -> 0.75f
                }
                val isSelected = act.id == selectedActivity?.id

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .offset(
                                x = (fx * 300).dp,
                                y = (fy * 180).dp
                            )
                            .size(if (isSelected) 36.dp else 28.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) AccentTerracotta else BrandForest)
                            .border(2.dp, CanvasBackground, CircleShape)
                            .clickable { onSelectMarker(act.id) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            color = CanvasBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isSelected) 13.sp else 11.sp
                        )
                    }
                }
            }

            // Map legend badge
            Surface(
                color = CanvasBackground.copy(alpha = 0.92f),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(AccentTerracotta)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${activeActivities.size} Clustered Stops",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandForestDark
                    )
                }
            }
        }

        // Selected stop highlight card with Route Optimization Insights
        if (selectedActivity != null) {
            Surface(
                color = SurfaceWarm,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandForestLight)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(AccentTerracotta),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "★",
                                    color = CanvasBackground,
                                    fontSize = 11.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SELECTED STOP INSIGHTS",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentTerracotta,
                                letterSpacing = 1.1.sp
                            )
                        }

                        Button(
                            onClick = { onOpenPlaceDetail(selectedActivity) },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandForest)
                        ) {
                            Text(text = "View Details", fontSize = 11.sp, color = CanvasBackground)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = selectedActivity.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "📍 ${selectedActivity.locationName} · ${selectedActivity.time}",
                        fontSize = 12.sp,
                        color = TextMuted
                    )

                    if (selectedActivity.transitToNext != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = SurfaceSand,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DirectionsCar,
                                    contentDescription = null,
                                    tint = BrandForest,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Transit to next: ${selectedActivity.transitToNext.durationText} (${selectedActivity.transitToNext.distanceText})",
                                    fontSize = 11.sp,
                                    color = BrandForestDark,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Ordered Stops List
        Text(
            text = "SEQUENTIAL WAYPOINTS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp,
            color = BrandForestDark,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(activeActivities) { index, act ->
                val isSelected = act.id == selectedActivity?.id
                Surface(
                    onClick = { onSelectMarker(act.id) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) SurfaceSand else CanvasBackground,
                    border = androidx.compose.foundation.BorderStroke(
                        if (isSelected) 1.5.dp else 1.dp,
                        if (isSelected) BrandForest else HairpinBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) AccentTerracotta else BrandForest),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = CanvasBackground,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = act.title,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "${act.time} · ${act.locationName}",
                                fontSize = 11.5.sp,
                                color = TextMuted
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
