package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.data.SampleData
import com.example.model.SavedTripItem
import com.example.model.ScreenState
import com.example.ui.theme.*

@Composable
fun SavedTripsScreen(
    activeTab: String,
    onTabSelect: (String) -> Unit,
    onResumeTrip: (String) -> Unit,
    onShareTrip: (String) -> Unit,
    onDuplicateTrip: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val upcomingCount = remember { SampleData.savedTrips.count { it.status == "Upcoming" } }
    val draftsCount = remember { SampleData.savedTrips.count { it.status == "Drafts" } }
    val completedCount = remember { SampleData.savedTrips.count { it.status == "Completed" } }

    val filteredList = remember(activeTab) {
        SampleData.savedTrips.filter { it.status == activeTab }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground)
            .padding(bottom = 70.dp)
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWarm)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Travel Archive & Saved Trips",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = TextPrimary
            )
            Text(
                text = "Manage ongoing itineraries, drafts, and past travel memories.",
                fontSize = 13.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Tabs for Upcoming, Drafts, Completed
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabSpecs = listOf(
                    "Upcoming" to "Upcoming ($upcomingCount)",
                    "Drafts" to "Drafts ($draftsCount)",
                    "Completed" to "Completed ($completedCount)"
                )

                tabSpecs.forEach { (tabKey, label) ->
                    val isSelected = activeTab == tabKey
                    Surface(
                        onClick = { onTabSelect(tabKey) },
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) BrandForest else SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("saved_tab_${tabKey.lowercase()}")
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) CanvasBackground else TextPrimary,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }

        // List of Saved Trip Cards
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredList) { trip ->
                SavedTripCard(
                    trip = trip,
                    onResume = { onResumeTrip(trip.id) },
                    onShare = { onShareTrip(trip.title) },
                    onDuplicate = { onDuplicateTrip(trip.title) }
                )
            }
        }
    }
}

@Composable
private fun SavedTripCard(
    trip: SavedTripItem,
    onResume: () -> Unit,
    onShare: () -> Unit,
    onDuplicate: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = SurfaceWarm,
        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("saved_card_${trip.id}")
    ) {
        Column {
            // Photo Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                AsyncImage(
                    model = trip.imageUrl,
                    contentDescription = trip.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Status pill
                Surface(
                    color = when (trip.status) {
                        "Upcoming" -> BrandForest
                        "Drafts" -> AccentTerracotta
                        else -> Color(0xFF636A63)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                ) {
                    Text(
                        text = trip.status.uppercase(),
                        color = CanvasBackground,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                // Budget badge
                Surface(
                    color = CanvasBackground.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                ) {
                    Text(
                        text = "₹${"%,d".format(trip.spentInr)} / ₹${"%,d".format(trip.targetInr)}",
                        color = BrandForestDark,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Info Body
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = trip.title,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = AccentTerracotta,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = trip.dates,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = trip.companions,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = HairpinBorder)
                Spacer(modifier = Modifier.height(10.dp))

                // Action buttons: Resume, Share, Duplicate
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = onShare,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = BrandForest,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        IconButton(
                            onClick = onDuplicate,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Duplicate",
                                tint = TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Button(
                        onClick = onResume,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandForest,
                            contentColor = CanvasBackground
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = if (trip.status == "Completed") "View Trip" else "Resume Planning",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
