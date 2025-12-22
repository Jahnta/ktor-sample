package com.example.data.event

import com.example.data.equipment.EquipmentEntity
import data.event.EventEntity
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class EventRepository {

    suspend fun getAll(): List<EventWithChildrenDto> = suspendTransaction {
        EventEntity.all().map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): EventWithChildrenDto? = suspendTransaction {
        EventEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: EventDto): EventDto = suspendTransaction {
        val entity = EventEntity.new {
            name = dto.name
            shortName = dto.shortName
            equipment = dto.equipmentId?.let { EquipmentEntity.Companion.findById(it) }

            dateStart = dto.dateStart
            dateEnd = dto.dateStart
            status = dto.status

            type = dto.type
            customAttributes = dto.customAttributes
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: EventDto): Boolean = suspendTransaction {
        val entity = EventEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            equipment = dto.equipmentId?.let { EquipmentEntity.Companion.findById(it) }

            dateStart = dto.dateStart
            dateEnd = dto.dateStart
            status = dto.status

            type = dto.type
            customAttributes = dto.customAttributes
        }
        true
    }

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = EventEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}