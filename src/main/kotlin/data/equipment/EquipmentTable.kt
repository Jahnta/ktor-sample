package com.example.data.equipment

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import com.example.data.powerunit.PowerUnitTable

object EquipmentTable: IntIdTable("equipment") {
    val name = varchar("name", 200)
    val shortName = varchar("short_name", 100).nullable()
    val powerUnitId = reference("power_unit_id", PowerUnitTable, onDelete = ReferenceOption.SET_NULL).nullable()

    val type = varchar("type", 100).nullable()
    val custom_attributes = text("custom_attributes").nullable()
}
