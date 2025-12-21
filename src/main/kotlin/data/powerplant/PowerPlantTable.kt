package com.example.data.powerplant

import com.example.data.area.AreaTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import com.example.data.organization.OrganizationTable

object PowerPlantTable: IntIdTable("power_plants") {
    val name = varchar("name", 200)
    val shortName = varchar("short_name", 100).nullable()
    val parentId = reference("parent_id", OrganizationTable, onDelete = ReferenceOption.SET_NULL).nullable()

    val areaId = reference("area_id", AreaTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val address = varchar("address", 100).nullable()
    val email = varchar("email", 50).nullable()
    val website = varchar("website", 50).nullable()
    val phoneNumber = varchar("phone_number", 50).nullable()

    val type = varchar("type", 100).nullable()
    val electricalPower = double("electrical_power").nullable()
    val thermalPower = double("thermal_power").nullable()
}
