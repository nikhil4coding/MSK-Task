package com.msktask.domain.model

sealed interface DataResult {
    data class Success(val data: List<MSKData>) : DataResult
    data class Error(val errorMessage: String) : DataResult
}