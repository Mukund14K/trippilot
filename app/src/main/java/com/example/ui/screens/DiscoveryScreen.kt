package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.model.DestinationHighlight
import com.example.ui.theme.*

@Composable
fun DiscoveryScreen(
    onPlanDestination: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBucket by remember { mutableStateOf("All") }

    val buckets = listOf(
        "All",
        "Weekend Escapes Under ₹10,000",
        "Culinary Pilgrimages",
        "Untouched Coastal Sanctuaries"
    )

    val filtered = remember(selectedBucket) {
        if (selectedBucket == "All") SampleData.destinations
        else SampleData.destinations.filter { it.bucket == selectedBucket }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground)
            .padding(bottom = 70.dp)
    ) {
        // Editorial Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceWarm)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Surface(
                color = SurfaceSand,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
            ) {
                Text(
                    text = "CURATED TRAVEL ARCHIVE",
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentTerracotta,
                    letterSpacing = 1.1.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Destination Discovery",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                color = TextPrimary
            )
            Text(
                text = "Hand-curated regions categorized by travel intent and budget bracket.",
                fontSize = 13.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Bucket Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                buckets.forEach { bucket ->
                    val isSelected = selectedBucket == bucket
                    Surface(
                        onClick = { selectedBucket = bucket },
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) BrandForest else SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                        modifier = Modifier.testTag("filter_bucket_${bucket.take(6).lowercase()}")
                    ) {
                        Text(
                            text = bucket,
                            fontSize = 11.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) CanvasBackground else TextPrimary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // Masonry-style Editorial Cards List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 14.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filtered) { dest ->
                EditorialDestinationCard(
                    dest = dest,
                    onPlan = {
                        val parsedBudget = when {
                            dest.name == "Goa" -> 25000
                            dest.name == "Jaipur" -> 12000
                            dest.name == "Hampi" -> 9000
                            dest.name == "Manali" -> 11000
                            dest.name == "Udaipur" -> 15000
                            else -> 22000
                        }
                        onPlanDestination(dest.name, parsedBudget)
                    }
                )
            }
        }
    }
}

@Composable
private fun EditorialDestinationCard(
    dest: DestinationHighlight,
    onPlan: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = SurfaceWarm,
        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("discovery_card_${dest.name.lowercase()}")
    ) {
        Column {
            // Photo Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = dest.imageUrl,
                    contentDescription = dest.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0xAA133829))
                            )
                        )
                )

                // Intent Bucket tag
                Surface(
                    color = AccentTerracotta,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = dest.bucket,
                        color = CanvasBackground,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                // Season pill
                Surface(
                    color = CanvasBackground.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Best: ${dest.bestSeason}",
                        color = BrandForestDark,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Narrative Body
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = dest.name,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = dest.state,
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "EST. BUDGET",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentTerracotta,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = dest.budgetEstimate,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = BrandForestDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = dest.description,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Transit info tag
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.NearMe,
                        contentDescription = null,
                        tint = AccentAmber,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = dest.travelTime,
                        fontSize = 11.5.sp,
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Tags strip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    dest.tags.forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceSand,
                            border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                        ) {
                            Text(
                                text = "#$tag",
                                fontSize = 10.5.sp,
                                color = BrandForestDark,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onPlan,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandForest,
                        contentColor = CanvasBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("plan_dest_btn_${dest.name.lowercase()}")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = AccentTerracotta,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Plan ${dest.name} Itinerary →",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
