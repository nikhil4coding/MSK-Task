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
                        type = toTypeUI(it.type),
                        value = it.value
                    )
                }
            )
        }

        private fun toTypeUI(type: String): ResultTypeUI {
            return when (type) {
                "AUTO" -> ResultTypeUI.AUTO
                "MANUAL" -> ResultTypeUI.MANUAL
                else -> {
                    ResultTypeUI.ERROR
                }
            }
        }
    }
}

data class ResultsUI(
    val id: String,
    val desc: String,
    val type: ResultTypeUI,
    val value: Int?
)

enum class ResultTypeUI {
    AUTO,
    MANUAL,
    ERROR
}