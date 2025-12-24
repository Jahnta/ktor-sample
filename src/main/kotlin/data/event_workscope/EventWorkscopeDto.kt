package com.example.data.event_workscope

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.time.Duration

@Serializable
data class EventWorkscopeDto(
    @SerialName("id") val id: Int,
    @SerialName("workscope_name") val workscopeName: String,
    @SerialName("workscope_id") val workscopeId: Int,
    @SerialName("workscope_description") val description: String? = null,

    @SerialName("duration") val duration: Duration? = null,
    @SerialName("status") val status: String? = null,

    @SerialName("custom_attributes") val customAttributes: Map<String, JsonElement>? = null
)