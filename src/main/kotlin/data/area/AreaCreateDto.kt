package com.example.data.area

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaCreateDto(
    @SerialName("name") val name: String,
    @SerialName("parent_id") val parentId: Int? = null,
)