package com.azkar.simple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class Zikr(val text: String, val count: Int, var current: Int = count)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AzkarApp()
        }
    }
}

@Composable
fun AzkarApp() {
    var azkar by remember {
        mutableStateOf(listOf(
            Zikr("أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ", 1),
            Zikr("اللَّهُمَّ بِكَ أَصْبَحْنَا، وَبِكَ أَمْسَيْنَا", 1),
            Zikr("اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ", 1),
            Zikr("رَضِيتُ بِاللَّهِ رَبًّا، وَبِالْإِسْلَامِ دِينًا", 3),
            Zikr("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ", 100)
        ))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "أذكار الصباح",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                azkar = azkar.map { it.copy(current = it.count) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("إعادة الكل")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(azkar) { zikr ->
                ZikrCard(
                    zikr = zikr,
                    onDone = {
                        azkar = azkar.map {
                            if (it == zikr && it.current > 0) {
                                it.copy(current = it.current - 1)
                            } else it
                        }
                    },
                    onReset = {
                        azkar = azkar.map {
                            if (it == zikr) it.copy(current = it.count) else it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ZikrCard(
    zikr: Zikr,
    onDone: () -> Unit,
    onReset: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = if (zikr.current == 0) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = zikr.text,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${zikr.current}/${zikr.count}",
                    style = MaterialTheme.typography.h6,
                    color = if (zikr.current == 0) Color(0xFF4CAF50) else Color.Black
                )

                Row {
                    Button(
                        onClick = onDone,
                        enabled = zikr.current > 0,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("تم")
                    }

                    OutlinedButton(onClick = onReset) {
                        Text("إعادة")
                    }
                }
            }
        }
    }
}

