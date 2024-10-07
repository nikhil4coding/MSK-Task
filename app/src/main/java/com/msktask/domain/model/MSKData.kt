package com.msktask.domain.model

import com.msktask.data.model.MSKDataJsonDTO

data class MSKData(
    val id: String,
    val desc: String,
    val sync: Boolean = true,
    var updated: String?,
    val validity: Int?,
    val results: List<Results>
) {
    companion object {
        fun fromDTO(dataDTO: MSKDataJsonDTO): MSKData {
            return MSKData(
                id = dataDTO.id,
                desc = dataDTO.desc,
                sync = dataDTO.sync,
                updated = dataDTO.updated,
                validity = dataDTO.validity,
                results = dataDTO.results.map {
                    Results(
                        id = it.id,
                        desc = it.desc,
                        type = it.type,
                        value = it.value
                    )
                }
            )
        }
    }
}

data class Results(
    val id: String,
    val desc: String,
    val type: String,
    val value: Int?
)