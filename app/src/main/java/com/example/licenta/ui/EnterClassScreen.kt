package com.example.licenta.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.licenta.viewmodel.ScheduleViewModel

// Material standard imports
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.AccessTime

// Wear Compose imports
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyColumnDefaults
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text

enum class ScreenState {
    CLASS_INPUT,
    DAY_SELECTION,
    SCHEDULE_DISPLAY
}

@Composable
fun EnterClassScreen(viewModel: ScheduleViewModel = viewModel()) {
    var classId by remember { mutableStateOf("") }
    var screenState by remember { mutableStateOf(ScreenState.CLASS_INPUT) }
    var selectedDay by remember { mutableStateOf("") }
    val scheduleState = viewModel.schedule.collectAsState()
    val schedule = scheduleState.value

    // Culori bazate pe tema din React
    val primaryColor = Color(0xFFF7BA34)      // #f7ba34 - galben
    val secondaryColor = Color(0xFF69A79C)    // #69a79c - verde
    val lightColor = Color(0xFFF7F7F7)        // #f7f7f7 - aproape alb
    val darkColor = Color(0xFF333333)         // #333333 - gri închis
    val dark2Color = Color(0xFF999999)        // #999999 - gri

    // Pentru OLED, folosim negru pentru background
    val backgroundColor = Color.Black
    val surfaceColor = Color(0xFF1A1A1A)     // Gri foarte închis pentru carduri

    Scaffold(
        timeText = {
            TimeText(
                modifier = Modifier.padding(8.dp),
                timeTextStyle = TimeTextDefaults.timeTextStyle(
                    fontSize = 10.sp,
                    color = primaryColor
                )
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(horizontal = 8.dp)
        ) {
            when (screenState) {
                ScreenState.CLASS_INPUT -> {
                    // ECRAN 1: Introducere clasă
                    ScalingLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        contentPadding = PaddingValues(
                            top = 20.dp,
                            bottom = 20.dp
                        )
                    ) {
                        item {
                            // Text școală simplu în loc de icon
                            Text(
                                "🏫",
                                fontSize = 48.sp,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }

                        item {
                            Text(
                                "KLASSE EINGEBEN",
                                color = lightColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }

                        item {
                            // Input cu stil
                            OutlinedTextField(
                                value = classId,
                                onValueChange = { classId = it.uppercase() },
                                modifier = Modifier
                                    .fillMaxWidth(0.75f)
                                    .height(56.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    textColor = lightColor,
                                    cursorColor = primaryColor,
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = dark2Color,
                                    backgroundColor = surfaceColor
                                ),
                                singleLine = true,
                                placeholder = {
                                    Text("Z.B: 9A",
                                        fontSize = 18.sp,
                                        color = dark2Color,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                shape = RoundedCornerShape(28.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(40.dp)) // Mărit spațiul de la 24dp la 40dp

                            // Buton confirmare în stil primary
                            Button(
                                onClick = {
                                    if (classId.isNotBlank()) {
                                        screenState = ScreenState.DAY_SELECTION
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = if (classId.isNotBlank()) primaryColor else surfaceColor
                                ),
                                shape = RoundedCornerShape(28.dp),
                                enabled = classId.isNotBlank()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = "Weiter",
                                        tint = if (classId.isNotBlank()) darkColor else dark2Color,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "WEITER",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (classId.isNotBlank()) darkColor else dark2Color
                                    )
                                }
                            }
                        }
                    }
                }

                ScreenState.DAY_SELECTION -> {
                    // ECRAN 2: Selectare zi
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Header cu clasa selectată
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 8.dp), // Adăugat padding orizontal
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(modifier = Modifier.width(8.dp)) // Spațiu suplimentar la stânga

                            IconButton(
                                onClick = {
                                    screenState = ScreenState.CLASS_INPUT
                                },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Zurück",
                                    tint = primaryColor
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Box(
                                modifier = Modifier
                                    .background(primaryColor, RoundedCornerShape(16.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    classId,
                                    color = darkColor,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(40.dp))
                        }

                        Text(
                            "TAG WÄHLEN",
                            color = lightColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        // Lista zilelor
                        ScalingLazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 20.dp,
                                bottom = 40.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            state = rememberScalingLazyListState(initialCenterItemIndex = 2),
                            scalingParams = ScalingLazyColumnDefaults.scalingParams(
                                edgeScale = 0.8f,
                                edgeAlpha = 0.7f
                            )
                        ) {
                            // Zilele săptămânii în germană
                            val days = listOf("Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag")
                            val romanianDays = listOf("Luni", "Marți", "Miercuri", "Joi", "Vineri")

                            items(days.size) { index ->
                                val isEven = index % 2 == 0
                                Chip(
                                    onClick = {
                                        selectedDay = days[index]
                                        // Trimitem ziua în română către API
                                        viewModel.fetchSchedulesForDay(classId, romanianDays[index])
                                        screenState = ScreenState.SCHEDULE_DISPLAY
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .height(60.dp),
                                    colors = ChipDefaults.chipColors(
                                        backgroundColor = if (isEven) primaryColor else secondaryColor
                                    ),
                                    label = {
                                        Text(
                                            days[index].uppercase(),
                                            fontSize = 20.sp,
                                            color = if (isEven) darkColor else Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                ScreenState.SCHEDULE_DISPLAY -> {
                    // ECRAN 3: Afișare orar
                    if (schedule == null) {
                        // Loading state
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "Lädt...",
                                color = dark2Color,
                                fontSize = 16.sp
                            )
                        }
                    } else if (schedule.isEmpty()) {
                        // Nu sunt ore
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "📚",
                                fontSize = 48.sp,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )

                            Text(
                                text = "KEINE STUNDEN",
                                color = primaryColor,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "$classId • $selectedDay",
                                color = dark2Color,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                            Button(
                                onClick = {
                                    screenState = ScreenState.DAY_SELECTION
                                    viewModel.clearSchedule()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = primaryColor
                                ),
                                shape = RoundedCornerShape(24.dp),
                                modifier = Modifier.height(48.dp)
                            ) {
                                Text("ZURÜCK",
                                    fontSize = 16.sp,
                                    color = darkColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        // Sortăm orele după ora de început
                        val sortedSchedule = remember(schedule) {
                            schedule.sortedBy { item ->
                                val time = item.startTime.split(":")
                                time[0].toIntOrNull() ?: 0
                            }
                        }

                        // Afișare ore
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Header fix
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.width(8.dp)) // Spațiu suplimentar la stânga

                                IconButton(
                                    onClick = {
                                        screenState = ScreenState.DAY_SELECTION
                                        viewModel.clearSchedule()
                                    },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = "Zurück",
                                        tint = primaryColor
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        classId,
                                        color = primaryColor,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        selectedDay,
                                        color = dark2Color,
                                        fontSize = 14.sp
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Spacer(modifier = Modifier.width(40.dp))
                            }

                            // Lista de ore sortate
                            ScalingLazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    top = 8.dp,
                                    bottom = 40.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                scalingParams = ScalingLazyColumnDefaults.scalingParams(
                                    edgeScale = 0.75f,
                                    edgeAlpha = 0.6f
                                )
                            ) {
                                items(sortedSchedule.size) { index ->
                                    val item = sortedSchedule[index]
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth(0.92f)
                                            .height(80.dp),
                                        backgroundColor = surfaceColor,
                                        shape = RoundedCornerShape(20.dp),
                                        elevation = 2.dp
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 16.dp, vertical = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Număr oră cu stil alternativ
                                            Box(
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .background(
                                                        color = if (index % 2 == 0) primaryColor else secondaryColor,
                                                        shape = CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "${index + 1}",
                                                    color = if (index % 2 == 0) darkColor else Color.White,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(16.dp))

                                            // Detalii oră
                                            Column(
                                                modifier = Modifier.weight(1f),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = item.subjects.firstOrNull() ?: "Stunde",
                                                    color = lightColor,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )

                                                Spacer(modifier = Modifier.height(4.dp))

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        Icons.Default.AccessTime,
                                                        contentDescription = "Zeit",
                                                        tint = dark2Color,
                                                        modifier = Modifier.size(14.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        text = "${item.startTime} - ${item.endTime}",
                                                        color = dark2Color,
                                                        fontSize = 14.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}