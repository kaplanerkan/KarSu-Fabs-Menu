package com.karsu.pandafabsmenu.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import karsu.libs.fabsmenu.compose.state.ExpandDirection
import karsu.libs.fabsmenu.compose.state.LabelsPosition

/**
 * Settings bottom sheet content for configuring the FAB menu.
 */
@Composable
fun SettingsSheetContent(
    expandDirection: ExpandDirection,
    labelsPosition: LabelsPosition,
    animationDuration: Int,
    onExpandDirectionChange: (ExpandDirection) -> Unit,
    onLabelsPositionChange: (LabelsPosition) -> Unit,
    onAnimationDurationChange: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        // Title
        Text(
            text = "Menu Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Expand Direction
        SettingSection(title = "Expand Direction") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DirectionButton(
                    icon = Icons.Default.ArrowUpward,
                    label = "UP",
                    isSelected = expandDirection == ExpandDirection.UP,
                    onClick = { onExpandDirectionChange(ExpandDirection.UP) }
                )
                DirectionButton(
                    icon = Icons.Default.ArrowDownward,
                    label = "DOWN",
                    isSelected = expandDirection == ExpandDirection.DOWN,
                    onClick = { onExpandDirectionChange(ExpandDirection.DOWN) }
                )
                DirectionButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    label = "LEFT",
                    isSelected = expandDirection == ExpandDirection.LEFT,
                    onClick = { onExpandDirectionChange(ExpandDirection.LEFT) }
                )
                DirectionButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    label = "RIGHT",
                    isSelected = expandDirection == ExpandDirection.RIGHT,
                    onClick = { onExpandDirectionChange(ExpandDirection.RIGHT) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Labels Position
        SettingSection(title = "Labels Position") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PositionButton(
                    label = "LEFT",
                    isSelected = labelsPosition == LabelsPosition.LEFT,
                    onClick = { onLabelsPositionChange(LabelsPosition.LEFT) }
                )
                PositionButton(
                    label = "RIGHT",
                    isSelected = labelsPosition == LabelsPosition.RIGHT,
                    onClick = { onLabelsPositionChange(LabelsPosition.RIGHT) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Animation Duration
        SettingSection(title = "Animation Duration: ${animationDuration}ms") {
            Slider(
                value = animationDuration.toFloat(),
                onValueChange = { onAnimationDurationChange(it.toInt()) },
                valueRange = 100f..1000f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFE91E63),
                    activeTrackColor = Color(0xFFE91E63)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("100ms", fontSize = 12.sp, color = Color.Gray)
                Text("1000ms", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Preview
        SettingSection(title = "Preview") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = when (expandDirection) {
                    ExpandDirection.UP -> Alignment.BottomEnd
                    ExpandDirection.DOWN -> Alignment.TopEnd
                    ExpandDirection.LEFT -> Alignment.CenterEnd
                    ExpandDirection.RIGHT -> Alignment.CenterStart
                }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = when (labelsPosition) {
                        LabelsPosition.LEFT -> Alignment.End
                        LabelsPosition.RIGHT -> Alignment.Start
                    },
                    verticalArrangement = if (expandDirection == ExpandDirection.UP) {
                        Arrangement.Bottom
                    } else {
                        Arrangement.Top
                    }
                ) {
                    // Preview FAB items
                    repeat(2) { index ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (labelsPosition == LabelsPosition.LEFT) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("Label", fontSize = 10.sp)
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(Color(0xFFE91E63), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            if (labelsPosition == LabelsPosition.RIGHT) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("Label", fontSize = 10.sp)
                                }
                            }
                        }
                        if (index < 1) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Main FAB
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFE91E63), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Apply Button
        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Settings")
        }
    }
}

@Composable
private fun SettingSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        content()
    }
}

@Composable
private fun DirectionButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Color(0xFFE91E63).copy(alpha = 0.1f)
                else Color.Transparent
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFFE91E63) else Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFFE91E63) else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) Color(0xFFE91E63) else Color.Gray
        )
    }
}

@Composable
private fun PositionButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Color(0xFFE91E63).copy(alpha = 0.1f)
                else Color.Transparent
            )
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFFE91E63) else Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = label,
                color = if (isSelected) Color(0xFFE91E63) else Color.Gray,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

// Previews
@Preview(showBackground = true)
@Composable
private fun SettingsSheetContentPreview() {
    SettingsSheetContent(
        expandDirection = ExpandDirection.UP,
        labelsPosition = LabelsPosition.LEFT,
        animationDuration = 500,
        onExpandDirectionChange = {},
        onLabelsPositionChange = {},
        onAnimationDurationChange = {},
        onDismiss = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun DirectionButtonPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        DirectionButton(
            icon = Icons.Default.ArrowUpward,
            label = "UP",
            isSelected = true,
            onClick = {}
        )
        DirectionButton(
            icon = Icons.Default.ArrowDownward,
            label = "DOWN",
            isSelected = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PositionButtonPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        PositionButton(
            label = "LEFT",
            isSelected = true,
            onClick = {}
        )
        PositionButton(
            label = "RIGHT",
            isSelected = false,
            onClick = {}
        )
    }
}
