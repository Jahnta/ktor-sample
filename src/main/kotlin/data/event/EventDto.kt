package com.example.data.event

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class EventDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("equipment_id") val equipmentId: Int? = null,

    @SerialName("date_start") val dateStart: LocalDate? = null,
    @SerialName("date_end") val dateEnd: LocalDate? = null,
    @SerialName("status") val status: String? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("custom_attributes") val customAttributes: Map<String, JsonElement>? = null,
)