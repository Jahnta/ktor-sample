package com.example.data.equipment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("power_unit_id") val powerUnitId: Int? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("custom_attributes") val customAttributes: String? = null,
)