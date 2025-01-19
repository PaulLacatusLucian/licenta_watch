import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.rememberLazyListState

@Composable
fun EnterClassScreen(viewModel: ScheduleViewModel = viewModel()) {
    var classId by remember { mutableStateOf("") }
    val scheduleState = viewModel.schedule.collectAsState()
    val schedule = scheduleState.value
    val listState = rememberLazyListState()

    // Detectare dacă lista nu este la început (pentru a ascunde titlul mai repede)
    val showTitle by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset < 50 && listState.firstVisibleItemIndex == 0 }
    }

    // Culori tematice
    val backgroundColor = Color(0xFFF7F7F7) // Culoare de fundal
    val cardColor = Color(0xFF333333) // Culoare pentru carduri
    val accentColor = Color(0xFFF7BA34) // Culoare primară
    val textColor = Color(0xFF333333) // Culoare pentru text
    val grayText = Color(0xFF69A79C) // Text gri

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        when {
            schedule == null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Please enter the Class Name",
                        color = accentColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = classId,
                        onValueChange = { classId = it },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(40.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = textColor,
                            cursorColor = accentColor,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = grayText
                        ),
                        singleLine = true,
                        placeholder = {
                            Text("Class Name", fontSize = 12.sp, color = grayText)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (classId.isNotBlank()) {
                                viewModel.fetchSchedulesForTomorrow(classId)
                            } else {
                                println("Class ID cannot be empty")
                            }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .height(32.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = accentColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("OK", color = textColor, fontSize = 12.sp)
                    }
                }
            }

            schedule.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nicio oră programată",
                        color = grayText,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (showTitle) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Today's",
                                color = accentColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Classes",
                                color = accentColor,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(schedule) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                backgroundColor = cardColor,
                                shape = RoundedCornerShape(8.dp),
                                elevation = 4.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = item.subjects.firstOrNull() ?: "",
                                        color = accentColor,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "${item.startTime} - ${item.endTime}",
                                        color = grayText,
                                        fontSize = 10.sp
                                    )

                                    Text(
                                        text = item.teacher?.name ?: "Profesor necunoscut",
                                        color = grayText,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }
            }
        }
    }
}
