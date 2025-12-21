package powerUnits

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import powerPlants.PowerPlantTable

object PowerUnitTable: IntIdTable("power_units") {
    val fullName = varchar("full_name", 200)
    val shortName = varchar("short_name", 100)
    val plantId = reference("plant_id", PowerPlantTable, onDelete = ReferenceOption.SET_NULL).nullable()

    val type = varchar("type", 100).nullable()
    val capacity = double("capacity").nullable()
}
