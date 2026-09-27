package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.model.BudgetBreakdown
import com.example.model.OptimizationSuggestion
import com.example.ui.theme.*

@Composable
fun BudgetHealthScreen(
    budget: BudgetBreakdown,
    suggestions: List<OptimizationSuggestion>,
    onApplySuggestion: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .padding(bottom = 75.dp)
    ) {
        // Title
        Text(
            text = "Budget Health & Analytics",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = TextPrimary
        )
        Text(
            text = "Real-time expense distribution against your ₹${"%,d".format(budget.totalTarget)} ceiling.",
            fontSize = 13.sp,
            color = TextMuted
        )

        Spacer(modifier = Modifier.height(18.dp))

        // High-level 3 Metric Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Total Allocated
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceSand,
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "TOTAL COMMITTED",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentTerracotta,
                        letterSpacing = 1.1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₹${"%,d".format(budget.totalSpent)}",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = BrandForestDark
                    )
                    Text(
                        text = "of ₹${"%,d".format(budget.totalTarget)}",
                        fontSize = 10.5.sp,
                        color = TextMuted
                    )
                }
            }

            // Remaining Buffer
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceSand,
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "SAFETY BUFFER",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = BrandForestDark,
                        letterSpacing = 1.1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₹${"%,d".format(budget.remainingBalance)}",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (budget.remainingBalance >= 0) AccentSage else AccentTerracotta
                    )
                    Text(
                        text = "Emergency reserve",
                        fontSize = 10.5.sp,
                        color = TextMuted
                    )
                }
            }

            // Daily Average
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = SurfaceSand,
                border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "DAILY AVERAGE",
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 1.1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₹${"%,d".format(budget.dailyAverage)}",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "5 days / group",
                        fontSize = 10.5.sp,
                        color = TextMuted
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Horizontal Stacked Visual Bar
        Text(
            text = "ALLOCATION BY CATEGORY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp,
            color = BrandForestDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        val total = budget.totalSpent.toFloat().coerceAtLeast(1f)
        val stayWeight = budget.stay / total
        val transWeight = budget.transport / total
        val foodWeight = budget.food / total
        val actWeight = budget.activities / total

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(18.dp)
                .clip(RoundedCornerShape(9.dp))
        ) {
            Box(
                modifier = Modifier
                    .weight(stayWeight.coerceAtLeast(0.05f))
                    .fillMaxHeight()
                    .background(Color(0xFF1B4332))
            )
            Box(
                modifier = Modifier
                    .weight(transWeight.coerceAtLeast(0.05f))
                    .fillMaxHeight()
                    .background(Color(0xFFD96B43))
            )
            Box(
                modifier = Modifier
                    .weight(foodWeight.coerceAtLeast(0.05f))
                    .fillMaxHeight()
                    .background(Color(0xFFE07A5F))
            )
            Box(
                modifier = Modifier
                    .weight(actWeight.coerceAtLeast(0.05f))
                    .fillMaxHeight()
                    .background(Color(0xFFE9C46A))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Itemized Breakdown Table
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SurfaceWarm,
            border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                BudgetItemRow(name = "Stay & Accommodations", amount = budget.stay, color = Color(0xFF1B4332), icon = Icons.Default.Hotel)
                HorizontalDivider(color = HairpinBorder, modifier = Modifier.padding(vertical = 10.dp))
                BudgetItemRow(name = "Local Transport & Cabs", amount = budget.transport, color = Color(0xFFD96B43), icon = Icons.Default.DirectionsCar)
                HorizontalDivider(color = HairpinBorder, modifier = Modifier.padding(vertical = 10.dp))
                BudgetItemRow(name = "Dining & Heritage Cafes", amount = budget.food, color = Color(0xFFE07A5F), icon = Icons.Default.Restaurant)
                HorizontalDivider(color = HairpinBorder, modifier = Modifier.padding(vertical = 10.dp))
                BudgetItemRow(name = "Activities & Tickets", amount = budget.activities, color = Color(0xFFE9C46A), icon = Icons.Default.ConfirmationNumber)
                HorizontalDivider(color = HairpinBorder, modifier = Modifier.padding(vertical = 10.dp))
                BudgetItemRow(name = "Unallocated Buffer", amount = budget.buffer, color = Color(0xFF52B788), icon = Icons.Default.Savings)
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // TripPilot Optimization Assistant Panel
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = SurfaceSand,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, AccentTerracotta.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AccentTerracotta),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingDown,
                            contentDescription = null,
                            tint = CanvasBackground,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "TripPilot Optimization Assistant",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Actionable suggestions to preserve buffer",
                            fontSize = 11.5.sp,
                            color = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                suggestions.forEach { item ->
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = CanvasBackground,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (item.isApplied) AccentSage else HairpinBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .testTag("suggestion_card_${item.id}")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.title,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = TextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    color = if (item.isApplied) Color(0xFFE3F5E7) else Color(0xFFFDE8E8),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = if (item.isApplied) "Applied (Saved ₹${item.savingsInr})" else "Save ₹${item.savingsInr}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.isApplied) BrandForest else AccentTerracotta,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = item.description,
                                fontSize = 12.sp,
                                color = TextMuted,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = { onApplySuggestion(item.id) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (item.isApplied) SurfaceSand else BrandForest,
                                    contentColor = if (item.isApplied) TextPrimary else CanvasBackground
                                ),
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .height(36.dp)
                                    .testTag("apply_suggestion_btn_${item.id}")
                            ) {
                                Icon(
                                    imageVector = if (item.isApplied) Icons.Default.Check else Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp),
                                    tint = if (item.isApplied) BrandForest else AccentTerracotta
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (item.isApplied) "Revert Change" else "Apply Suggestion",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BudgetItemRow(
    name: String,
    amount: Int,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = name,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Text(
            text = "₹${"%,d".format(amount)}",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 14.5.sp,
            color = TextPrimary
        )
    }
}
