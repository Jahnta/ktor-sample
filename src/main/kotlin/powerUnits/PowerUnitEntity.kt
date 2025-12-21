package powerUnits

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import powerPlants.PowerPlantEntity

class PowerUnitEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PowerUnitEntity>(PowerUnitTable)

    var fullName by PowerUnitTable.fullName
    var shortName by PowerUnitTable.shortName
    var plant by PowerPlantEntity optionalReferencedOn PowerUnitTable.plantId

    var type by PowerUnitTable.type
    var capacity by PowerUnitTable.capacity


    fun toDto() = PowerUnitDto(
        id = id.value,
        fullName = fullName,
        shortName = shortName,
        plantId = plant?.id?.value,

        type = type,
        capacity = capacity,
    )
}