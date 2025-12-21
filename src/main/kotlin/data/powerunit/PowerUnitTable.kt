package com.example.data.powerUnits

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import com.example.data.powerPlants.PowerPlantTable

object PowerUnitTable: IntIdTable("power_units") {
    val name = varchar("name", 200)
    val shortName = varchar("short_name", 100).nullable()
    val powerPlantId = reference("power_plant_id", PowerPlantTable, onDelete = ReferenceOption.SET_NULL).nullable()

    val type = varchar("type", 100).nullable()
    val capacity = double("capacity").nullable()
}
