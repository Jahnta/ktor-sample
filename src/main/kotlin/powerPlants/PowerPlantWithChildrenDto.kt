package powerPlants

import area.AreaDto
import kotlinx.serialization.Serializable
import powerUnits.PowerUnitDto

@Serializable
data class PowerPlantWithChildrenDto(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val parentId: Int? = null,

    val area: AreaDto? = null,
    val address: String? = null,
    val email: String? = null,
    val website: String? = null,
    val phoneNumber: String? = null,

    val type: String? = null,
    val electricalPower: Double? = null,
    val thermalPower: Double? = null,

    val powerUnits: List<PowerUnitDto> = emptyList()
)
