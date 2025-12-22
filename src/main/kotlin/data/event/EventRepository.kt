package com.example.data.equipment

import com.example.data.event.EventDto
import com.example.data.powerunit.PowerUnitEntity
import data.event.EventEntity
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class EventRepository {

    suspend fun getAll(): List<EventDto> = suspendTransaction {
        EventEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: Int): EventDto? = suspendTransaction {
        EventEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: EventDto): EventDto = suspendTransaction {
        val entity = EventEntity.new {
            name = dto.name
            shortName = dto.shortName
            equipment = dto.equipmentId?.let { EquipmentEntity.findById(it) }

            dateStart = dto.dateStart
            dateEnd = dto.dateStart

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
            equipment = dto.equipmentId?.let { EquipmentEntity.findById(it) }

            dateStart = dto.dateStart
            dateEnd = dto.dateStart

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