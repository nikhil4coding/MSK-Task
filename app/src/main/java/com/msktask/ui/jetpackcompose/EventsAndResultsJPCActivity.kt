package com.msktask.ui.jetpackcompose

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msktask.R
import com.msktask.ui.jetpackcompose.viewmodel.EventsAndResultsJPCViewModel
import com.msktask.ui.model.MSKDataUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsAndResultsJPCActivity : ComponentActivity() {

    private val viewModel: EventsAndResultsJPCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadEvents()
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

        val viewState: EventsAndResultsJPCViewModel.ViewState by viewModel.viewState.collectAsStateWithLifecycle()


        when (viewState) {
            is EventsAndResultsJPCViewModel.ViewState.Error -> {
                Toast.makeText(
                    this, stringResource(
                        R.string.error,
                        (viewState as EventsAndResultsJPCViewModel.ViewState.Error).errorCode
                    ), Toast.LENGTH_SHORT
                ).show()
            }

            EventsAndResultsJPCViewModel.ViewState.Loading -> isLoading = true
            is EventsAndResultsJPCViewModel.ViewState.Success -> {
                isLoading = (viewState as EventsAndResultsJPCViewModel.ViewState.Success).isLoading
                eventList = (viewState as EventsAndResultsJPCViewModel.ViewState.Success).data
            }

            EventsAndResultsJPCViewModel.ViewState.Idle -> {}
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
            val intent = Intent(activity, EventsAndResultsJPCActivity::class.java)
            return intent
        }
    }
}