package com.example.quizapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Result Of Your Quiz",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 200.dp)
        )

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "You Have Scored",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "20%",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuizStat(
                        modifier = Modifier.weight(1f).padding(18.dp),
                        prefix = "Q",
                        value = "10",
                        label = "Total Que",
                        textColor = Color(0xFF2196F3)
                    )

                    Divider(
                        modifier = Modifier
                            .height(100.dp)
                            .width(1.dp),
                        color = Color.LightGray
                    )

                    QuizStat(
                        modifier = Modifier.weight(1f).padding(18.dp),
                        prefix = "✓",
                        value = "2",
                        label = "Correct",
                        textColor = Color(0xFF4CAF50)
                    )

                    Divider(
                        modifier = Modifier
                            .height(100.dp)
                            .width(1.dp),
                        color = Color.LightGray
                    )

                    QuizStat(
                        modifier = Modifier.weight(1f).padding(18.dp),
                        prefix = "✕",
                        value = "8",
                        label = "Wrong",
                        textColor = Color(0xFFF44336)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Back To Home",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun QuizStat(
    modifier: Modifier = Modifier,
    prefix: String,
    value: String,
    label: String,
    textColor: Color
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = prefix,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}