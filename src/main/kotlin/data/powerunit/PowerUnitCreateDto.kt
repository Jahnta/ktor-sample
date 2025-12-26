package com.example.data.powerunit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerUnitCreateDto(
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("power_plant_id") val powerPlantId: Int? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("capacity") val capacity: Double? = null,
)
