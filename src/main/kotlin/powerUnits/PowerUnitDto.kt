package powerUnits

import kotlinx.serialization.Serializable

@Serializable
data class PowerUnitDto(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val plantId: Int? = null,

    val type: String? = null,
    val capacity: Double? = null,
)
