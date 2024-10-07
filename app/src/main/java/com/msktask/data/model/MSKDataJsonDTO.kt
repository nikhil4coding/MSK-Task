package com.msktask.data.model

import com.google.gson.annotations.SerializedName

typealias DataRoot = List<MSKDataJsonDTO>;

data class MSKDataJsonDTO(
    @SerializedName("id") val id: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("sync") val sync: Boolean = true,
    @SerializedName("updated" ) val updated : String?,
    @SerializedName("validity" ) val validity : Int?,
    @SerializedName("results") val results: List<ResultsDTO>
)

data class ResultsDTO(
    @SerializedName("id") val id: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: Int?
)