package area

import kotlinx.serialization.Serializable

@Serializable
data class AreaWithChildrenDto(
    val id: Int,
    val name: String,
    val parentId: Int? = null,
    val areas: List<AreaWithChildrenDto> = emptyList()
)
