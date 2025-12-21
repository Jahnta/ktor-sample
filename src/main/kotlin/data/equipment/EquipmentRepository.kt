package com.example.data.equipment

import com.example.data.powerUnits.PowerUnitEntity
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class EquipmentRepository {

    suspend fun getAll(): List<EquipmentDto> = suspendTransaction {
        EquipmentEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: Int): EquipmentDto? = suspendTransaction {
        EquipmentEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: EquipmentDto): EquipmentDto = suspendTransaction {
        val entity = EquipmentEntity.new {
            name = dto.name
            shortName = dto.shortName
            powerUnit = dto.powerUnitId?.let { PowerUnitEntity.findById(it) }

            type = dto.type
            customAttributes = dto.customAttributes
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: EquipmentDto): Boolean = suspendTransaction {
        val entity = EquipmentEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            powerUnit = dto.powerUnitId?.let { PowerUnitEntity.findById(it) }

            type = dto.type
            customAttributes = dto.customAttributes
        }
        true
    }

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = EquipmentEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}