package com.example.data.powerplant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PowerPlantCreateDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("parent_id") val parentId: Int? = null,

    @SerialName("area_id") val areaId: Int? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("electrical_power") val electricalPower: Double? = null,
    @SerialName("thermal_power") val thermalPower: Double? = null,
)