package com.example.data.event_workscope

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class EventWorkscopeDto(
    @SerialName("id") val id: Int,
    @SerialName("workScope_name") val workScopeName: String,
    @SerialName("workscope_id") val workscopeId: Int,
    @SerialName("description") val description: String? = null,

    @SerialName("duration") val duration: Duration? = null,
    @SerialName("status") val status: String? = null,
)