package com.example.organization

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationCreateDto(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val parentId: Int? = null,

    val areaId: Int? = null,
    val address: String? = null,
    val email: String? = null,
    val website: String? = null,
    val phoneNumber: String? = null,
)