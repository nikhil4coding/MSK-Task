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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msktask.R
import com.msktask.ui.jetpackcompose.EventsAndResultsJPCActivity
import com.msktask.ui.oldxml.EventsAndResultsXMLActivity
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
                        val intent = EventsAndResultsXMLActivity.getInstance(this)
                        this.startActivity(intent)
                    },
                    onJPComposeClicked = {
                        val intent = EventsAndResultsJPCActivity.getInstance(this)
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
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            onClick = { onXmlClicked() }) {
            Text(
                fontSize = 24.sp,
                text = stringResource(R.string.xml_layout_btn)
            )
        }


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onJPComposeClicked() }) {
            Text(
                fontSize = 24.sp,
                text = stringResource(R.string.jetpack_compose_layout_btn)
            )
        }

    }
}