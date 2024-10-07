package com.msktask.ui.event.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msktask.domain.model.DataResult
import com.msktask.domain.usecase.GetDataUseCase
import com.msktask.ui.model.MSKDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {
    private val eventViewStateEmitter = MutableLiveData<EventViewState>()
    val eventViewState: LiveData<EventViewState> = eventViewStateEmitter

    private val eventExceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, exception ->
        Timber.d("Error: " + exception.message)
    }

    fun loadEvents() {
        eventViewStateEmitter.postValue(EventViewState.Loading)
        viewModelScope.launch(eventExceptionHandler) {
            launch(Dispatchers.IO) {
                when (val data = getDataUseCase.getData()) {
                    is DataResult.Error -> {
                        eventViewStateEmitter.postValue(EventViewState.Error(data.errorMessage))
                    }

                    is DataResult.Success -> {
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

    sealed interface EventViewState {
        data object Loading : EventViewState
        data class Success(val data: List<MSKDataUI>, val isLoading: Boolean = false) : EventViewState
        data class Error(val errorCode: String) : EventViewState
    }
}