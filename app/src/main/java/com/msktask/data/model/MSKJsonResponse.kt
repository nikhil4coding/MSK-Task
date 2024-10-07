package com.msktask.data.model

sealed interface MSKJsonResponse {
    data class Success(val data: DataRoot): MSKJsonResponse
    data class Error(val errorMessage: String): MSKJsonResponse
}