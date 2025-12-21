package com.example.data.organization

import com.example.data.area.AreaDto
import kotlinx.serialization.Serializable
import com.example.data.powerPlants.PowerPlantDto
import kotlinx.serialization.SerialName

@Serializable
data class OrganizationWithChildrenDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("short_name") val shortName: String? = null,
    @SerialName("parent_id") val parentId: Int? = null,

    @SerialName("area") val area: AreaDto? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("phone_number") val phoneNumber: String? = null,

    @SerialName("organizations") val organizations: List<OrganizationWithChildrenDto> = emptyList(),
    @SerialName("power_plants") val powerPlants: List<PowerPlantDto> = emptyList()
)