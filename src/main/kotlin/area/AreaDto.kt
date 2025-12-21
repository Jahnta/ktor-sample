package area

import kotlinx.serialization.Serializable

@Serializable
data class AreaDto(
    val id: Int,
    val name: String,
    val parentId: Int? = null,
)