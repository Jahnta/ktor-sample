package data.event

import com.example.data.equipment.EquipmentEntity
import com.example.data.event.EventDto
import com.example.data.event.EventTable
import com.example.data.event.EventWithChildrenDto
import com.example.data.event_workscope.EventWorkscopeTable
import com.example.data.workscope.WorkscopeEntity
import data.event_workscope.EventWorkscopeEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class EventEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EventEntity>(EventTable)

    var name by EventTable.name
    var shortName by EventTable.shortName
    var equipment by EquipmentEntity optionalReferencedOn EventTable.equipmentId

    var dateStart by EventTable.dateStart
    var dateEnd by EventTable.dateEnd
    var status by EventTable.status

    var type by EventTable.type
    var customAttributes by EventTable.customAttributes

    val workscopeItems by EventWorkscopeEntity referrersOn EventWorkscopeTable.eventId

    fun toDto() = EventDto(
        id = id.value,
        name = name,
        shortName = shortName,
        equipmentId = equipment?.id?.value,

        dateStart = dateStart,
        dateEnd = dateEnd,
        status = status,

        type = type,
        customAttributes = customAttributes,
    )

    fun toDtoWithChildren(): EventWithChildrenDto {
        return EventWithChildrenDto(
            id = id.value,
            name = name,
            shortName = shortName,
            equipmentId = equipment?.id?.value,

            dateStart = dateStart,
            dateEnd = dateEnd,
            status = status,

            type = type,
            customAttributes = customAttributes,
            workscopeItems = workscopeItems.map { it.toDto() }
        )
    }
}