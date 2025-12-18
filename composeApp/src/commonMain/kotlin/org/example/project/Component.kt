package org.example.project

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun AnimatedCircle() {
    val infiniteTransition = rememberInfiniteTransition()

    val size by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(
                    color = Color.Blue,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
        )
    }
}