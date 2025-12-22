package com.example.data.event

import com.example.data.equipment.EquipmentTable
import com.example.data.powerunit.PowerUnitTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.date

object EventTable: IntIdTable("event") {
    val name = varchar("name", 200)
    val shortName = varchar("short_name", 100).nullable()
    val equipmentId = reference("equipment_id", EquipmentTable, onDelete = ReferenceOption.SET_NULL).nullable()

    val dateStart = date("date_start").nullable()
    val dateEnd = date("date_end").nullable()

    val type = varchar("type", 100).nullable()
    val custom_attributes = text("custom_attributes").nullable()
}