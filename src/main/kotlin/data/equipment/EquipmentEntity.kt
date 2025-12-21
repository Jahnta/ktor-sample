package com.example.data.equipment

import com.example.data.area.AreaEntity
import com.example.data.organization.OrganizationDto
import com.example.data.organization.OrganizationTable
import com.example.data.organization.OrganizationWithChildrenDto
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import com.example.data.powerPlants.PowerPlantEntity
import com.example.data.powerPlants.PowerPlantTable
import com.example.data.powerUnits.PowerUnitEntity

class EquipmentEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EquipmentEntity>(EquipmentTable)

    var name by EquipmentTable.name
    var shortName by EquipmentTable.shortName
    var powerUnit by PowerUnitEntity optionalReferencedOn EquipmentTable.powerUnitId

    var type by EquipmentTable.type
    var customAttributes by EquipmentTable.custom_attributes


    fun toDto() = EquipmentDto(
        id = id.value,
        name = name,
        shortName = shortName,
        powerUnitId = powerUnit?.id?.value,

        type = type,
        customAttributes = customAttributes
    )
}