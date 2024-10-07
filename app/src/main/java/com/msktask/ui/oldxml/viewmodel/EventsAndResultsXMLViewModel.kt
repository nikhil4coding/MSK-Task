package com.msktask.ui.oldxml.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msktask.domain.model.DataResult
import com.msktask.domain.usecase.GetDataUseCase
import com.msktask.ui.model.MSKDataUI
import com.msktask.ui.model.ResultsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EventsAndResultsXMLViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {
    private var eventList: List<MSKDataUI> = emptyList()

    private val eventViewStateEmitter = MutableLiveData<EventViewState>()
    val eventViewState: LiveData<EventViewState> = eventViewStateEmitter

    private val selectedEventIdEmitter = MutableLiveData<String>()
    val selectedEventId: LiveData<String> = selectedEventIdEmitter

    private val resultsEmitter = MutableLiveData<List<ResultsUI>>()
    val resultsList: LiveData<List<ResultsUI>> = resultsEmitter

    private val eventExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, exception ->
        Timber.d("Error: " + exception.message)
    }

    fun loadEvents() {
        viewModelScope.launch(eventExceptionHandler) {
            launch(Dispatchers.IO) {
                when (val data = getDataUseCase.getData()) {
                    is DataResult.Error -> {
                        eventViewStateEmitter.postValue(EventViewState.Error(data.errorMessage))
                    }

                    is DataResult.Success -> {
                        eventList = data.data.map { MSKDataUI.fromData(it) }
                        eventViewStateEmitter.postValue(
                            EventViewState.Success(
                                data.data.map { MSKDataUI.fromData(it) }
                            )
                        )
                    }
                }
            }
        }
    }

    fun selectedEvent(eventId: String) {
        selectedEventIdEmitter.postValue(eventId)
    }

    fun getResultsForEvent(selectedEventId: String) {
        resultsEmitter.postValue(
            eventList.first { it.id == selectedEventId }.results
        )
    }

    sealed interface EventViewState {
        data class Success(val data: List<MSKDataUI>) : EventViewState
        data class Error(val errorCode: String) : EventViewState
    }
}