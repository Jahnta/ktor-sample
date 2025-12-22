package com.example.data.powerunit

import com.example.data.equipment.EquipmentEntity
import com.example.data.equipment.EquipmentTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import com.example.data.powerplant.PowerPlantEntity
import org.jetbrains.exposed.v1.core.eq

class PowerUnitEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PowerUnitEntity>(PowerUnitTable)

    var name by PowerUnitTable.name
    var shortName by PowerUnitTable.shortName
    var powerPlant by PowerPlantEntity optionalReferencedOn PowerUnitTable.powerPlantId

    var type by PowerUnitTable.type
    var capacity by PowerUnitTable.capacity


    fun toDto() = PowerUnitDto(
        id = id.value,
        name = name,
        shortName = shortName,
        powerPlantId = powerPlant?.id?.value,

        type = type,
        capacity = capacity,
    )

    fun toDtoWithChildren(): PowerUnitWithChildrenDto {
        val equipment = EquipmentEntity.find { EquipmentTable.powerUnitId eq this.id }.map { it.toDto() }

        return PowerUnitWithChildrenDto(
            id = id.value,
            name = name,
            shortName = shortName,
            powerPlantId = powerPlant?.id?.value,

            type = type,
            capacity = capacity,

            equipment = equipment,
        )
    }
}