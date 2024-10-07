package com.msktask.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.msktask.data.model.DataRoot
import com.msktask.data.model.MSKJsonResponse
import com.msktask.domain.JsonRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : JsonRepository {

    override suspend fun getData(): MSKJsonResponse {
        return try {
            val jsonString = context.assets.open("MSK_Data.json").bufferedReader().use {
                it.readText()
            }
            val jsonDataType = object : TypeToken<DataRoot>() {}.type
            val jsonData: DataRoot = Gson().fromJson(jsonString, jsonDataType)
            MSKJsonResponse.Success(jsonData)
        } catch (ioException: IOException) {
            MSKJsonResponse.Error("Error parsing JSON")
        }
    }
}