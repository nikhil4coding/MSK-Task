package com.msktask.ui.jetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msktask.domain.model.DataResult
import com.msktask.domain.usecase.GetDataUseCase
import com.msktask.ui.model.MSKDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EventsAndResultsJPCViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {
    private val viewStateEmitter = MutableStateFlow<ViewState>(ViewState.Idle)
    val viewState = viewStateEmitter.asStateFlow()

    private val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, exception ->
        Timber.d("Error: " + exception.message)
    }

    fun loadEvents() {
        viewStateEmitter.value = ViewState.Loading
        viewModelScope.launch(exceptionHandler) {
            launch(Dispatchers.IO) {
                when (val data = getDataUseCase.getData()) {
                    is DataResult.Error -> {
                        viewStateEmitter.value = ViewState.Error(data.errorMessage)
                    }

                    is DataResult.Success -> {
                        viewStateEmitter.value =
                            ViewState.Success(data.data.map { MSKDataUI.fromData(it) })
                    }
                }
            }
        }
    }

    sealed interface ViewState {
        data object Idle : ViewState
        data object Loading : ViewState
        data class Success(val data: List<MSKDataUI>, val isLoading: Boolean = false) : ViewState
        data class Error(val errorCode: String) : ViewState
    }
}