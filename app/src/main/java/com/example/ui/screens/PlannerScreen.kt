package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PlannerForm
import com.example.ui.theme.*

@Composable
fun PlannerScreen(
    currentStep: Int,
    form: PlannerForm,
    onDestinationChange: (String) -> Unit,
    onDatesChange: (String) -> Unit,
    onTravellersChange: (Int, Int) -> Unit,
    onBudgetChange: (Int) -> Unit,
    onToggleInterest: (String) -> Unit,
    onPaceChange: (String) -> Unit,
    onStyleChange: (String) -> Unit,
    onNextStep: () -> Unit,
    onPrevStep: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .padding(bottom = 70.dp)
    ) {
        // Step progress header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onPrevStep,
                modifier = Modifier.testTag("planner_back_button")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BrandForest
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "STEP $currentStep OF 7",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.3.sp,
                    color = AccentTerracotta
                )
                Text(
                    text = "Crafting Your Journey",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = TextPrimary
                )
            }

            Text(
                text = "${(currentStep * 100) / 7}%",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { currentStep / 7f },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = BrandForest,
            trackColor = HairpinBorder
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Step Content based on currentStep (1 to 7)
        when (currentStep) {
            1 -> Step1Destination(form.destination, onDestinationChange)
            2 -> Step2Dates(form.dateRange, onDatesChange)
            3 -> Step3Travellers(form.adults, form.children, onTravellersChange)
            4 -> Step4Budget(form.budgetInr, onBudgetChange)
            5 -> Step5Interests(form.selectedInterests, onToggleInterest)
            6 -> Step6Pace(form.pace, onPaceChange)
            7 -> Step7Style(form.travelStyle, onStyleChange)
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Bottom Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (currentStep > 1) {
                OutlinedButton(
                    onClick = onPrevStep,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder)
                ) {
                    Text(text = "Previous", fontWeight = FontWeight.SemiBold)
                }
            }

            Button(
                onClick = onNextStep,
                modifier = Modifier
                    .weight(if (currentStep > 1) 1.5f else 1f)
                    .height(50.dp)
                    .testTag("planner_next_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandForest,
                    contentColor = CanvasBackground
                )
            ) {
                Text(
                    text = if (currentStep == 7) "Build My Trip →" else "Continue →",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.5.sp
                )
            }
        }
    }
}

@Composable
private fun Step1Destination(destination: String, onChange: (String) -> Unit) {
    val quickPicks = listOf("Goa", "Jaipur", "Kerala", "Manali", "Udaipur", "Meghalaya", "Hampi")

    Column {
        Text(
            text = "Where are you going?",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "Select one of our curated hubs or enter any city in India.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        OutlinedTextField(
            value = destination,
            onValueChange = onChange,
            label = { Text("Destination Name") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = AccentTerracotta)
            },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("planner_dest_input"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SurfaceSand,
                unfocusedContainerColor = SurfaceWarm,
                focusedBorderColor = BrandForest,
                unfocusedBorderColor = HairpinBorder
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "POPULAR DESTINATIONS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp,
            color = BrandForestDark
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            quickPicks.take(4).forEach { pick ->
                val isSelected = pick.equals(destination, ignoreCase = true)
                Surface(
                    onClick = { onChange(pick) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) BrandForest else SurfaceSand,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) BrandForest else HairpinBorder)
                ) {
                    Text(
                        text = pick,
                        fontSize = 12.5.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) CanvasBackground else TextPrimary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Step2Dates(dates: String, onChange: (String) -> Unit) {
    val presets = listOf(
        "Oct 12 - Oct 17 (5 Days)",
        "This Long Weekend (3 Days)",
        "Next Month Mid-Week (4 Days)",
        "Nov 05 - Nov 12 (7 Days)"
    )

    Column {
        Text(
            text = "When are you going?",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "TripPilot adjusts daily pace according to daylight and seasonal sunset hours.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        presets.forEach { preset ->
            val isSelected = preset == dates
            Surface(
                onClick = { onChange(preset) },
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) SurfaceSand else CanvasBackground,
                border = androidx.compose.foundation.BorderStroke(
                    if (isSelected) 2.dp else 1.dp,
                    if (isSelected) BrandForest else HairpinBorder
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onChange(preset) },
                        colors = RadioButtonDefaults.colors(selectedColor = BrandForest)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = preset,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun Step3Travellers(adults: Int, children: Int, onChange: (Int, Int) -> Unit) {
    val presets = listOf(
        "Solo Explorer" to (1 to 0),
        "Couple" to (2 to 0),
        "Family (2+1)" to (2 to 1),
        "Friends Group (4)" to (4 to 0)
    )

    Column {
        Text(
            text = "Who's coming along?",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "Helps calibrate cab sizes, double occupancy rooms, and restaurant reservations.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        // Presets
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presets.forEach { (label, pair) ->
                val isSelected = adults == pair.first && children == pair.second
                Surface(
                    onClick = { onChange(pair.first, pair.second) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) BrandForest else SurfaceSand,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isSelected) CanvasBackground else TextPrimary,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                        maxLines = 1
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Adult Stepper
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = SurfaceSand,
            border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Adults", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                    Text(text = "Age 13 and above", fontSize = 12.sp, color = TextMuted)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onChange((adults - 1).coerceAtLeast(1), children) },
                        enabled = adults > 1
                    ) {
                        Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = "Decrease", tint = BrandForest)
                    }
                    Text(
                        text = "$adults",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { onChange(adults + 1, children) }) {
                        Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = "Increase", tint = BrandForest)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Children Stepper
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = SurfaceSand,
            border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Children", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                    Text(text = "Ages 2 - 12", fontSize = 12.sp, color = TextMuted)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { onChange(adults, (children - 1).coerceAtLeast(0)) },
                        enabled = children > 0
                    ) {
                        Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = "Decrease", tint = BrandForest)
                    }
                    Text(
                        text = "$children",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(onClick = { onChange(adults, children + 1) }) {
                        Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = "Increase", tint = BrandForest)
                    }
                }
            }
        }
    }
}

@Composable
private fun Step4Budget(budget: Int, onChange: (Int) -> Unit) {
    Column {
        Text(
            text = "What's your total budget?",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "TripPilot continuously manages stay, transit, and buffer margins within this ceiling.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = SurfaceSand,
            border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TARGET BUDGET",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentTerracotta,
                    letterSpacing = 1.1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${"%,d".format(budget)}",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp,
                    color = BrandForestDark
                )
                Text(
                    text = "≈ ₹${"%,d".format(budget / 5)} / day for your group",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(20.dp))

                Slider(
                    value = budget.toFloat(),
                    onValueChange = { onChange((it / 1000).toInt() * 1000) },
                    valueRange = 8000f..70000f,
                    steps = 30,
                    colors = SliderDefaults.colors(
                        thumbColor = AccentTerracotta,
                        activeTrackColor = BrandForest,
                        inactiveTrackColor = HairpinBorder
                    ),
                    modifier = Modifier.testTag("budget_slider")
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "₹8,000", fontSize = 11.sp, color = TextMuted)
                    Text(text = "₹35,000", fontSize = 11.sp, color = TextMuted)
                    Text(text = "₹70,000", fontSize = 11.sp, color = TextMuted)
                }
            }
        }
    }
}

@Composable
private fun Step5Interests(selected: Set<String>, onToggle: (String) -> Unit) {
    val interests = listOf(
        "Beaches", "Food & Dining", "Heritage", "Nature & Treks",
        "Nightlife & Lounges", "Art & Architecture", "Markets & Crafts", "Water Sports"
    )

    Column {
        Text(
            text = "What are you into?",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "Pick at least 2 interests to fine-tune our venue curation algorithms.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            interests.forEach { item ->
                val isChecked = selected.contains(item)
                Surface(
                    onClick = { onToggle(item) },
                    shape = RoundedCornerShape(14.dp),
                    color = if (isChecked) SurfaceSand else CanvasBackground,
                    border = androidx.compose.foundation.BorderStroke(
                        if (isChecked) 2.dp else 1.dp,
                        if (isChecked) BrandForest else HairpinBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { onToggle(item) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = BrandForest,
                                checkmarkColor = CanvasBackground
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = item,
                            fontSize = 14.sp,
                            fontWeight = if (isChecked) FontWeight.Bold else FontWeight.Medium,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Step6Pace(pace: String, onChange: (String) -> Unit) {
    val paces = listOf(
        Triple("Relaxed", "2 curated spots per day", "Late morning starts, long unhurried meals, and sunset downtime."),
        Triple("Balanced", "3 - 4 spots per day", "The golden medium: active mornings, cultural lunch, and evening strolls."),
        Triple("Packed", "5+ spots per day", "Maximum coverage for ambitious travelers who want to see every monument.")
    )

    Column {
        Text(
            text = "What is your travel pace?",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "We ensure transit intervals leave realistic room for unexpected delays.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        paces.forEach { (title, count, desc) ->
            val isSelected = pace == title
            Surface(
                onClick = { onChange(title) },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) SurfaceSand else CanvasBackground,
                border = androidx.compose.foundation.BorderStroke(
                    if (isSelected) 2.dp else 1.dp,
                    if (isSelected) BrandForest else HairpinBorder
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = BrandForestDark
                        )
                        Surface(
                            color = if (isSelected) BrandForest else SurfaceSand,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = count,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) CanvasBackground else TextMuted,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = desc,
                        fontSize = 12.5.sp,
                        color = TextMuted,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun Step7Style(style: String, onChange: (String) -> Unit) {
    val styles = listOf(
        Triple("Budget", "Hostels, street eats & local buses", "Maximize authentic local immersion while keeping costs tightly managed."),
        Triple("Balanced", "Boutique stays, heritage cafes & cabs", "Comfortable private rooms, air-conditioned taxis, and top-rated local dining."),
        Triple("Premium", "Luxury villas, fine dining & private chauffeur", "Curated high-end heritage properties, wine pairings, and zero logistical friction.")
    )

    Column {
        Text(
            text = "Choose your travel style",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = TextPrimary
        )
        Text(
            text = "Calibrates hotel suggestions, transport recommendations, and dining tiers.",
            fontSize = 14.sp,
            color = TextMuted,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        styles.forEach { (title, subtitle, desc) ->
            val isSelected = style == title
            Surface(
                onClick = { onChange(title) },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) SurfaceSand else CanvasBackground,
                border = androidx.compose.foundation.BorderStroke(
                    if (isSelected) 2.dp else 1.dp,
                    if (isSelected) BrandForest else HairpinBorder
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = BrandForestDark
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentTerracotta
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = desc,
                        fontSize = 12.5.sp,
                        color = TextMuted,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
