package com.example.data.equipment

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import com.example.data.powerunit.PowerUnitEntity
import com.example.plugins.JsonConfig

class EquipmentEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EquipmentEntity>(EquipmentTable)

    var name by EquipmentTable.name
    var shortName by EquipmentTable.shortName
    var powerUnit by PowerUnitEntity optionalReferencedOn EquipmentTable.powerUnitId

    var type by EquipmentTable.type
    var customAttributes by EquipmentTable.custom_attributes


    fun toDto() = EquipmentResponseDto(
        id = id.value,
        name = name,
        shortName = shortName,
        powerUnitId = powerUnit?.id?.value,

        type = type,
        customAttributes = customAttributes?.let { JsonConfig.json.decodeFromString(it) }
    )
}