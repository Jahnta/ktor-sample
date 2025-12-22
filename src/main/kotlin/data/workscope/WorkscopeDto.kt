package com.example.data.event

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class WorkscopeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("parent_id") val parentId: Int? = null,

    @SerialName("default_duration") val defaultDuration: Duration? = null,

    @SerialName("custom_attributes") val customAttributes: String? = null,
)