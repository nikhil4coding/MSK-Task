package com.msktask.domain

import com.msktask.data.model.MSKJsonResponse

interface JsonRepository {
    suspend fun getData(): MSKJsonResponse
}