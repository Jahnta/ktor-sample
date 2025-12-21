package powerPlants

import kotlinx.serialization.Serializable

@Serializable
data class PowerPlantCreateDto(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val parentId: Int? = null,

    val areaId: Int? = null,
    val address: String? = null,
    val email: String? = null,
    val website: String? = null,
    val phoneNumber: String? = null,

    val type: String? = null,
    val electricalPower: Double? = null,
    val thermalPower: Double? = null,
)