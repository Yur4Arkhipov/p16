package org.example.project

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
fun InteractiveCardGallery() {
    var selectedCard by remember { mutableStateOf<Int?>(null) }
    var cards by remember {
        mutableStateOf(
            listOf(
                CardData(1, "Космос", Color(0xFF1A237E)),
                CardData(2, "Океан", Color(0xFF006064)),
                CardData(3, "Лес", Color(0xFF1B5E20)),
                CardData(4, "Огонь", Color(0xFFBF360C)),
                CardData(5, "Молния", Color(0xFF4A148C))
            )
        )
    }
    var isExplosion by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = cards, key = { it.id }) { card ->
                AnimatedCard(
                    card = card,
                    isSelected = selectedCard == card.id,
                    pulseScale = if (selectedCard == card.id) pulseScale else 1f,
                    onClick = { selectedCard = if (selectedCard == card.id) null else card.id },
                    modifier = Modifier.animateItem(
                        fadeInSpec = tween(500),
                        placementSpec = tween(600),
                        fadeOutSpec = tween(500)
                    )
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { cards = cards.shuffled() }) {
                Text("Перемешать")
            }
            Button(onClick = {
                if (cards.isNotEmpty()) {
                    cards = cards.dropLast(1)
                }
            }) {
                Text("Удалить")
            }
            Button(onClick = {
                val newId = (cards.maxOfOrNull { it.id } ?: 0) + 1
                cards = cards + CardData(newId, "Новая #$newId", randomColor())
            }) {
                Text("Добавить")
            }
        }
    }
}

data class CardData(val id: Int, val title: String, val color: Color)

@Composable
fun AnimatedCard(
    card: CardData,
    isSelected: Boolean,
    pulseScale: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isSelected) 5f else 0f,
        animationSpec = spring(dampingRatio = 0.4f),
        label = "rotation"
    )
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 4.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth(0.85f)
            .graphicsLayer {
                scaleX = pulseScale
                scaleY = pulseScale
                rotationZ = rotation
            }
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = card.color),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Text(
            text = card.title,
            modifier = Modifier.padding(24.dp),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

fun randomColor(): Color {
    val colors = listOf(
        Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF3F51B5),
        Color(0xFF00BCD4), Color(0xFF4CAF50), Color(0xFFFF9800)
    )
    return colors.random()
}
