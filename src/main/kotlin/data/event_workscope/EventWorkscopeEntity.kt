package data.event_workscope

import com.example.data.event_workscope.EventWorkscopeDto
import com.example.data.event_workscope.EventWorkscopeTable
import com.example.data.workscope.WorkscopeEntity
import data.event.EventEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class EventWorkscopeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EventWorkscopeEntity>(EventWorkscopeTable)

    var event by EventEntity referencedOn EventWorkscopeTable.eventId
    var workscope by WorkscopeEntity referencedOn EventWorkscopeTable.workscopeId

    var duration by EventWorkscopeTable.duration
    var status by EventWorkscopeTable.status

    fun toDto() = EventWorkscopeDto(
        id = id.value,
        workscopeId = workscope.id.value,
        workScopeName = workscope.name,
        description = workscope.description,
        duration = duration,
        status = status
    )
}