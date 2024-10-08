package com.msktask.domain.usecase

import com.msktask.data.model.DataRoot
import com.msktask.data.model.MSKDataJsonDTO
import com.msktask.data.model.MSKJsonResponse
import com.msktask.domain.JsonRepository
import com.msktask.domain.model.DataResult
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetDataUseCaseTest {

    private lateinit var useCase: GetDataUseCase

    private val jsonRepository: JsonRepository = mock()

    @Before
    fun setUp() {
        useCase = GetDataUseCase(jsonRepository)
    }

    @Test
    fun `get Data returns error`() = runTest {
        whenever(jsonRepository.getData()).thenReturn(MSKJsonResponse.Error("Error"))

        val result = useCase.getData()
        assertEquals(DataResult.Error("Error"), result)
    }

    @Test
    fun `get Data returns Success`() = runTest {
        val dataRoot = emptyList<MSKDataJsonDTO>()
        whenever(jsonRepository.getData()).thenReturn(MSKJsonResponse.Success(dataRoot))

        val result = useCase.getData()
        assertEquals(DataResult.Success(emptyList()), result)
    }
}