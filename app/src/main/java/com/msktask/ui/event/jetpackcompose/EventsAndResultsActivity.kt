package com.msktask.ui.event.jetpackcompose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msktask.R
import com.msktask.ui.event.viewmodel.EventViewModel
import com.msktask.ui.model.MSKDataUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsAndResultsActivity : ComponentActivity() {

    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventViewModel.loadEvents()
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                SetupView()
            }
        }
    }

    @Composable
    private fun SetupView() {
        var isLoading: Boolean by rememberSaveable { mutableStateOf(false) }
        var eventList: List<MSKDataUI> by rememberSaveable {
            mutableStateOf(emptyList())
        }
        var selectedEvent: String by rememberSaveable { mutableStateOf("") }

        val navController = rememberNavController()

        when (val result = eventViewModel.eventViewState.observeAsState().value) {
            is EventViewModel.EventViewState.Error -> {
                Toast.makeText(this, stringResource(R.string.error, result.errorCode), Toast.LENGTH_SHORT).show()
            }

            EventViewModel.EventViewState.Loading -> isLoading = true
            is EventViewModel.EventViewState.Success -> {
                isLoading = result.isLoading
                eventList = result.data
            }

            null -> {}
        }

        NavHost(
            navController = navController, startDestination = EVENTS
        ) {
            composable(EVENTS) {
                EventListView(
                    isLoading = isLoading,
                    eventList = eventList,
                    onEventClicked = {
                        selectedEvent = it
                        navController.navigate(RESULTS)
                    },
                    onBackClicked = {
                        finish()
                    }
                )
            }

            composable(RESULTS) {
                ResultListView(
                    isLoading = isLoading,
                    resultList = eventList.find { it.id == selectedEvent }?.results ?: emptyList(),
                    onEventClicked = {},
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }

    companion object {
        fun getInstance(activity: Activity): Intent {
            val intent = Intent(activity, EventsAndResultsActivity::class.java)
            return intent
        }
    }
}