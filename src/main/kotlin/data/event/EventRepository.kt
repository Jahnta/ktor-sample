package com.example.data.event

import com.example.data.equipment.EquipmentEntity
import com.example.data.event_workscope.EventWorkscopeDto
import com.example.data.event_workscope.EventWorkscopeTable
import com.example.plugins.JsonConfig
import data.event.EventEntity
import data.event_workscope.EventWorkscopeEntity
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.with
import plugins.newSuspendTransaction

class EventRepository {

    suspend fun getAll(): List<EventDto> = newSuspendTransaction {
        EventEntity
            .all()
            .with(EventEntity::equipment)
            .with(EventEntity::workscopeItems)
            .map { it.toDto() }
    }

    suspend fun getById(id: Int): EventWithChildrenDto? = newSuspendTransaction {
        EventEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: EventDto): EventDto = newSuspendTransaction {
        val entity = EventEntity.new {
            name = dto.name
            shortName = dto.shortName
            equipment = dto.equipmentId?.let { EquipmentEntity.Companion.findById(it) }

            dateStart = dto.dateStart
            dateEnd = dto.dateStart
            status = dto.status

            type = dto.type
            customAttributes = dto.customAttributes?.let { JsonConfig.json.encodeToString(it) }
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: EventDto): Boolean = newSuspendTransaction {
        val entity = EventEntity.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            equipment = dto.equipmentId?.let { EquipmentEntity.findById(it) }

            dateStart = dto.dateStart
            dateEnd = dto.dateStart
            status = dto.status

            type = dto.type
            customAttributes = dto.customAttributes?.let { JsonConfig.json.encodeToString(it) }
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = EventEntity.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }

    suspend fun getEventWorkscopes(id: Int): List<EventWorkscopeDto> = newSuspendTransaction {
        EventWorkscopeEntity.find { EventWorkscopeTable.eventId eq id }.map { it.toDto() }
    }
}