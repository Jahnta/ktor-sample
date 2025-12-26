package com.example.data.equipment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class EquipmentResponseDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("power_unit_id") val powerUnitId: Int? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("custom_attributes") val customAttributes: Map<String, JsonElement>? = null,
)