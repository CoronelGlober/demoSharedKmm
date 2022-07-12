package com.david.demokmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import com.arkivanov.decompose.defaultComponentContext
import com.david.demokmm.components.RootComponentImpl
import com.david.demokmm.components.definitions.CounterComponent
import com.david.demokmm.components.definitions.AsciiComponent
import com.david.demokmm.components.definitions.RootComponent
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

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
//                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//                    CounterUi(rootComponent.counterChild)
//                    LettersUi(rootComponent.lettersChild)
//                }
            }
        }
    }

    @Composable
    private fun TittleUi(rootHost: RootComponent) {
        val numbers = rootHost.state.collectAsLazyPagingItems()
        LazyColumn {
            items(numbers) { character ->
                character?.let {
                    Text(text = it.toString(), fontSize = 25.sp, modifier = Modifier.padding(15.dp))
                }
            }
            when {
                numbers.loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                numbers.loadState.append is LoadState.Loading -> {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
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
