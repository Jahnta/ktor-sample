package com.example.data.powerplant

import com.example.data.area.AreaResponseDto
import kotlinx.serialization.Serializable
import com.example.data.powerunit.PowerUnitResponseDto
import kotlinx.serialization.SerialName

@Serializable
data class PowerPlantResponseWithChildrenDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("parent_id") val parentId: Int? = null,

    @SerialName("area") val area: AreaResponseDto? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,

    @SerialName("type") val type: String? = null,
    @SerialName("electrical_power") val electricalPower: Double? = null,
    @SerialName("thermal_power") val thermalPower: Double? = null,

    @SerialName("power_units") val powerUnits: List<PowerUnitResponseDto> = emptyList()
)
