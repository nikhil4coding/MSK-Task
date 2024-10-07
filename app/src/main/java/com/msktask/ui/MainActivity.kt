package com.msktask.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.msktask.ui.event.jetpackcompose.EventsAndResultsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainView(
                    onXmlClicked = {
                    },
                    onJPComposeClicked = {
                        val intent = EventsAndResultsActivity.getInstance(this)
                        this.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun MainView(
    onXmlClicked: () -> Unit,
    onJPComposeClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onXmlClicked() }) {
            Text("Xml Layout")
        }


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onJPComposeClicked() }) {
            Text("Jetpack Compose Layout")
        }

    }
}