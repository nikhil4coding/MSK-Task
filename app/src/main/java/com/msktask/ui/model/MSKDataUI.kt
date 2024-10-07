package com.msktask.ui.model

import com.msktask.domain.model.MSKData

data class MSKDataUI(
    val id: String,
    val desc: String,
    val sync: Boolean = true,
    var updated: String?,
    val validity: Int?,
    val results: List<ResultsUI>
) {
    companion object {
        fun fromData(data: MSKData): MSKDataUI {
            return MSKDataUI(
                id = data.id,
                desc = data.desc,
                sync = data.sync,
                updated = data.updated,
                validity = data.validity,
                results = data.results.map {
                    ResultsUI(
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

data class ResultsUI(
    val id: String,
    val desc: String,
    val type: String,
    val value: Int?
)