package com.example.smartmedicalsystem.ui.theme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val TealPrimary = Color(0xFF00604E)
val TealLight = Color(0xFFE0F2F1)
val TealDark = Color(0xFF004D40)
val OrangeAccent = Color(0xFFFF6B35)
val SurfaceGray = Color(0xFFF4F6F8)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = TealPrimary)
    )
}


@Composable
fun SectionHeader(number: Int, question: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .background(TealPrimary, CircleShape)
        ) {
            Text("$number", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Spacer(Modifier.width(10.dp))
        Text(question, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = TealDark)
    }
}


@Composable
fun ReportTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    maxLines: Int = 3,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        maxLines = maxLines,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = TealPrimary,
            focusedLabelColor = TealPrimary
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDropdown(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onExpandChange) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TealPrimary,
                focusedLabelColor = TealPrimary
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandChange(false) }) {
            options.forEach { opt ->
                DropdownMenuItem(text = { Text(opt) }, onClick = {
                    onSelect(opt)
                    onExpandChange(false)
                })
            }
        }
    }
}


@Composable
fun ReportSubmitButton(label: String, loading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = !loading,
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
    ) {
        if (loading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
        else Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}


@Composable
fun ReportCard(
    title: String,
    subtitle: String,
    date: String,
    icon: ImageVector,
    statusColor: Color = TealPrimary,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(44.dp).background(TealLight, RoundedCornerShape(12.dp))
            ) {
                Icon(icon, null, tint = TealPrimary, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
                Text(date, fontSize = 11.sp, color = Color.LightGray)
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(statusColor, CircleShape)
            )
        }
    }
}


@Composable
fun StatChip(label: String, value: String, color: Color = TealPrimary) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = color)
            Text(label, fontSize = 11.sp, color = color.copy(alpha = 0.8f))
        }
    }
}


@Composable
fun SimpleBarChart(
    data: List<Pair<String, Int>>,
    color: Color = TealPrimary,
    modifier: Modifier = Modifier
) {
    val maxVal = data.maxOfOrNull { it.second }?.coerceAtLeast(1) ?: 1
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth().height(120.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.forEach { (label, value) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("$value", fontSize = 10.sp, color = TealDark)
                    Spacer(Modifier.height(2.dp))
                    Box(
                        Modifier
                            .fillMaxWidth(0.6f)
                            .height((value.toFloat() / maxVal * 100).dp)
                            .background(color, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    )
                }
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.forEach { (label, _) ->
                Text(label, fontSize = 10.sp, color = Color.Gray, modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
fun PeerReviewActionRow(
    onApprove: () -> Unit,
    onDecline: () -> Unit,
    onAddDisease: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = onApprove,
            colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(Icons.Default.Check, null, Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text("Approve", fontSize = 13.sp)
        }
        Button(
            onClick = onDecline,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(Icons.Default.Close, null, Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text("Decline", fontSize = 13.sp)
        }
        OutlinedButton(
            onClick = onAddDisease,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary)
        ) {
            Text("+ Add", fontSize = 13.sp)
        }
    }
}