package com.example.data.area

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaWithChildrenDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("parent_id") val parentId: Int? = null,
    @SerialName("areas") val areas: List<AreaWithChildrenDto> = emptyList()
)
