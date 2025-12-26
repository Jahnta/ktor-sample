package com.example.data.organization

import com.example.data.area.AreaResponseDto
import kotlinx.serialization.Serializable
import com.example.data.powerplant.PowerPlantResponseDto
import kotlinx.serialization.SerialName

@Serializable
data class OrganizationResponseWithChildrenDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("parent_id") val parentId: Int? = null,

    @SerialName("area") val area: AreaResponseDto? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,

    @SerialName("organizations") val organizations: List<OrganizationResponseWithChildrenDto> = emptyList(),
    @SerialName("power_plants") val powerPlants: List<PowerPlantResponseDto> = emptyList()
)