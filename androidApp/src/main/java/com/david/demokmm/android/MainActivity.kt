package com.david.demokmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.defaultComponentContext
import com.david.demokmm.components.RootComponentImpl
import com.david.demokmm.components.definitions.CounterComponent
import com.david.demokmm.components.definitions.AsciiComponent
import com.david.demokmm.components.definitions.RootComponent

class MainActivity : ComponentActivity() {

    private val rootComponent: RootComponent by lazy { RootComponentImpl(componentContext = defaultComponentContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TittleUi(rootComponent)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CounterUi(rootComponent.counterChild)
                    LettersUi(rootComponent.lettersChild)
                }
            }
        }
    }

    @Composable
    private fun TittleUi(rootHost: RootComponent) {
        val state by rootHost.state.collectAsState()
        Text(
            text = "Counter = ${state.counter} - Char = ${state.character}",
            fontSize = MaterialTheme.typography.h5.fontSize
        )
    }

    @Composable
    fun CounterUi(counter: CounterComponent) {
        val state by counter.state.collectAsState()

        Column(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Blue), RoundedCornerShape(5)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = state.count.toString())
            Button(
                onClick = counter::incrementCounterBy10,
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Increment counter")
            }
        }
    }

    @Composable
    fun LettersUi(letters: AsciiComponent) {
        val state by letters.state.collectAsState()

        Column(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Red), RoundedCornerShape(5)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = state.character.toString())
            Button(
                onClick = letters::incrementAsciiValueBy10,
                modifier = Modifier.padding(10.dp)
            ) {
                Text("Next letter")
            }
        }
    }
}
