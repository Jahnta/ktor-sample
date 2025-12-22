package data.event

import com.example.data.equipment.EquipmentEntity
import com.example.data.equipment.EquipmentTable
import com.example.data.event.EventDto
import com.example.data.event.EventTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class EventEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EventEntity>(EventTable)

    var name by EventTable.name
    var shortName by EventTable.shortName
    var equipment by EquipmentEntity optionalReferencedOn EventTable.equipmentId

    var dateStart by EventTable.dateStart
    var dateEnd by EventTable.dateEnd

    var type by EventTable.type
    var customAttributes by EventTable.custom_attributes


    fun toDto() = EventDto(
        id = id.value,
        name = name,
        shortName = shortName,
        equipmentId = equipment?.id?.value,

        dateStart = dateStart,
        dateEnd = dateEnd,

        type = type,
        customAttributes = customAttributes
    )
}