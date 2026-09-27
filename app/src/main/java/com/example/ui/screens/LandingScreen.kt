package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.model.ScreenState
import com.example.ui.theme.*

@Composable
fun LandingScreen(
    onPlanTripClick: () -> Unit,
    onExploreClick: () -> Unit,
    onDestinationSelect: (String, Int) -> Unit,
    onQuickPlanSubmit: (String, String, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var quickDest by remember { mutableStateOf("Goa") }
    var quickBudget by remember { mutableStateOf(25000) }
    var quickTravellers by remember { mutableStateOf(3) }
    var quickDates by remember { mutableStateOf("Oct 12 - 17") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp)
    ) {
        // Hero Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Surface(
                color = SurfaceSand,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(AccentTerracotta)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "INTELLIGENT ITINERARY SEQUENCING",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.1.sp,
                        color = BrandForestDark
                    )
                }
            }

            Text(
                text = "Plan less.\nExperience more.",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                color = TextPrimary,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Build smarter trips around your time, budget, and the places you actually want to experience. No generic templates — clustered by proximity and paced for real humans.",
                fontSize = 14.5.sp,
                lineHeight = 22.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Dual Hero CTAs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onPlanTripClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandForest,
                        contentColor = CanvasBackground
                    ),
                    modifier = Modifier
                        .weight(1.2f)
                        .height(48.dp)
                        .testTag("hero_plan_trip_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = AccentTerracotta,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Plan a Trip",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                OutlinedButton(
                    onClick = onExploreClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextPrimary
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("hero_explore_button")
                ) {
                    Text(
                        text = "Destinations",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.5.sp
                    )
                }
            }
        }

        // Authentic Photography Grid / Collage
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1.3f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800&auto=format&fit=crop&q=80",
                    contentDescription = "Goa Fort Aguada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0x99133829))
                            )
                        )
                )
                Text(
                    text = "Goa · Coastal Drift",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = CanvasBackground,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1603288940316-24e5ef952f40?w=800&auto=format&fit=crop&q=80",
                        contentDescription = "Jaipur Hawa Mahal",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Jaipur",
                        fontWeight = FontWeight.Bold,
                        color = CanvasBackground,
                        fontSize = 11.5.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    AsyncImage(
                        model = "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?w=800&auto=format&fit=crop&q=80",
                        contentDescription = "Kerala Tea Hills",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Kerala",
                        fontWeight = FontWeight.Bold,
                        color = CanvasBackground,
                        fontSize = 11.5.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Interactive Planning Teaser: Inline mini-bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(20.dp),
            color = SurfaceWarm,
            border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = AccentTerracotta,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "QUICK TRIP PARAMETERS",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.1.sp,
                        color = BrandForestDark
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Mini bar pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Destination selector pill
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                quickDest = if (quickDest == "Goa") "Jaipur" else if (quickDest == "Jaipur") "Kerala" else "Goa"
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "DESTINATION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                            Text(text = quickDest, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        }
                    }

                    // Budget selector pill
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                quickBudget = if (quickBudget == 25000) 15000 else if (quickBudget == 15000) 35000 else 25000
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "BUDGET", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                            Text(text = "₹${quickBudget / 1000}k", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = BrandForestDark)
                        }
                    }

                    // Travellers pill
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                quickTravellers = if (quickTravellers == 3) 2 else if (quickTravellers == 2) 4 else 3
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "TRAVELLERS", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                            Text(text = "$quickTravellers People", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        onQuickPlanSubmit(quickDest, quickDates, quickBudget, quickTravellers)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("submit_quick_plan_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandForest,
                        contentColor = CanvasBackground
                    )
                ) {
                    Text(
                        text = "Build $quickDest Itinerary →",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.5.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Value Prop Breakdown: Scattered Planning vs TripPilot Optimized
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "HOW TRIPPILOT OPTIMIZES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = AccentTerracotta
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "The Science of Better Travel",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                color = SurfaceSand,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Scattered Planning Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFDE8E8)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color(0xFFC53030),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Traditional Scattered Planning",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = TextPrimary
                            )
                            Text(
                                text = "Zig-zagging across cities, 3+ hours lost daily in traffic, sudden budget surprises, and rushed museum closes.",
                                fontSize = 12.sp,
                                color = TextMuted,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = HairpinBorder)
                    Spacer(modifier = Modifier.height(14.dp))

                    // TripPilot Optimized Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE3F5E7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = BrandForest,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "TripPilot Proximity Clustering",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = BrandForestDark
                            )
                            Text(
                                text = "Clustered geographic nodes, guaranteed sunset timing, real-time pace pacing, and proactive ₹ budget buffers.",
                                fontSize = 12.sp,
                                color = TextMuted,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Curated Destination Highlights
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "POPULAR DESTINATIONS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = AccentTerracotta
                    )
                    Text(
                        text = "Curated Indian Escapes",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TextPrimary
                    )
                }

                TextButton(onClick = onExploreClick) {
                    Text(
                        text = "View all (8) →",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandForest
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                SampleData.destinations.take(4).forEach { dest ->
                    Surface(
                        onClick = { onDestinationSelect(dest.name, 25000) },
                        modifier = Modifier
                            .width(220.dp)
                            .testTag("dest_card_${dest.name.lowercase()}"),
                        shape = RoundedCornerShape(16.dp),
                        color = SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                            ) {
                                AsyncImage(
                                    model = dest.imageUrl,
                                    contentDescription = dest.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Surface(
                                    color = CanvasBackground.copy(alpha = 0.9f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = dest.bestSeason,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = BrandForestDark,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = dest.name,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = TextPrimary
                                )
                                Text(
                                    text = dest.state,
                                    fontSize = 11.5.sp,
                                    color = TextMuted
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = dest.budgetEstimate,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandForestDark
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
