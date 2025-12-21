package com.example.data.organization

import com.example.data.area.AreaTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object OrganizationTable : IntIdTable("organizations") {
    val name = varchar("name", 200)
    val shortName = varchar("short_name", 100).nullable()
    val parentId = reference("parent_id", OrganizationTable, onDelete = ReferenceOption.SET_NULL).nullable()

    val areaId = reference("area_id", AreaTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val address = varchar("address", 100).nullable()
    val email = varchar("email", 50).nullable()
    val website = varchar("website", 50).nullable()
    val phoneNumber = varchar("phone_number", 50).nullable()
}