package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiAssistantModal(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onApplyPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isOpen) return

    var promptInput by remember { mutableStateOf("") }

    val quickChips = listOf(
        "Make Day 2 more relaxed",
        "Add authentic Goan seafood lunch",
        "Find sunset spots with less crowd",
        "Cut Day 3 budget by ₹1,000"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CanvasBackground,
        dragHandle = {
            BottomSheetDefaults.DragHandle(color = HairpinBorder)
        },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 36.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(BrandForest),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoFixHigh,
                            contentDescription = null,
                            tint = AccentTerracotta,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Refine with TripPilot",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Natural-Language Itinerary Optimizer",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SUGGESTED REFINEMENTS",
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp,
                color = AccentTerracotta
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Quick suggestion chips
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                quickChips.forEach { chipText ->
                    Surface(
                        onClick = {
                            onApplyPrompt(chipText)
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = SurfaceSand,
                        border = androidx.compose.foundation.BorderStroke(1.dp, HairpinBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("ai_chip_${chipText.take(10).replace(" ", "_").lowercase()}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = AccentTerracotta,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = chipText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "OR DESCRIBE YOUR CUSTOM ADJUSTMENT",
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp,
                color = BrandForestDark
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Input field
            OutlinedTextField(
                value = promptInput,
                onValueChange = { promptInput = it },
                placeholder = {
                    Text(
                        text = "e.g., Push morning starts to 10:30 AM and add an architectural walk.",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ai_custom_input"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CanvasBackground,
                    unfocusedContainerColor = SurfaceSand,
                    focusedBorderColor = BrandForest,
                    unfocusedBorderColor = HairpinBorder,
                    cursorColor = BrandForest
                ),
                maxLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (promptInput.isNotBlank()) {
                            onApplyPrompt(promptInput)
                            promptInput = ""
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    if (promptInput.isNotBlank()) {
                        onApplyPrompt(promptInput)
                        promptInput = ""
                    }
                },
                enabled = promptInput.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("ai_submit_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandForest,
                    contentColor = CanvasBackground
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = AccentTerracotta,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Apply AI Optimization",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
