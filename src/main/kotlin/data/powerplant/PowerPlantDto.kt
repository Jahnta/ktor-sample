package com.example.data.powerPlants

import com.example.data.area.AreaDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerPlantDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("parent_id") val parentId: Int? = null,

    @SerialName("area") val area: AreaDto? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("electrical_power") val electricalPower: Double? = null,
    @SerialName("thermal_power") val thermalPower: Double? = null,
)
