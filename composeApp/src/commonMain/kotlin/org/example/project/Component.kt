package org.example.project

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LazyColumnAnimation() {
    var list by remember {
        mutableStateOf(listOf("Пушкин", "Есенин", "Маяковский", "Толстой", "Достоевский", "Чехов"))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = list, key = { it }) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
                    .background(Color.LightGray)
                    .padding(24.dp)
                    .animateItem(
                        fadeInSpec = tween(durationMillis = 1200),
                        placementSpec = tween(durationMillis = 1200),
                        fadeOutSpec = tween(durationMillis = 1200)
                    ),
                text = it
            )
        }

        item {
            Button(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                onClick = { list = list.shuffled() }
            ) {
                Text(text = "Перемешать")
            }
        }
    }
}


@Composable
fun ClickCounter(
    modifier: Modifier = Modifier
) {
    var count by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Счётчик: $count",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { count++ }) {
                Text("+")
            }
            Button(onClick = { count = 0 }) {
                Text("Сброс")
            }
        }
    }
}