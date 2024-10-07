package com.msktask.domain.usecase

import com.msktask.data.model.MSKJsonResponse
import com.msktask.domain.JsonRepository
import com.msktask.domain.model.DataResult
import com.msktask.domain.model.MSKData
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val jsonRepository: JsonRepository
) {

    suspend fun getData(): DataResult {
        return when (val response = jsonRepository.getData()) {
            is MSKJsonResponse.Error -> {
                DataResult.Error(response.errorMessage)
            }

            is MSKJsonResponse.Success -> {
                DataResult.Success(
                    response.data.map { MSKData.fromDTO(it) }
                )
            }
        }
    }
}