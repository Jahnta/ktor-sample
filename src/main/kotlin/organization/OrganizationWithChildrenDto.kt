package organization

import area.AreaDto
import kotlinx.serialization.Serializable
import powerPlants.PowerPlantDto

@Serializable
data class OrganizationWithChildrenDto(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val parentId: Int? = null,

    val area: AreaDto? = null,
    val address: String? = null,
    val email: String? = null,
    val website: String? = null,
    val phoneNumber: String? = null,

    val organizations: List<OrganizationWithChildrenDto> = emptyList(),
    val plants: List<PowerPlantDto> = emptyList()
)