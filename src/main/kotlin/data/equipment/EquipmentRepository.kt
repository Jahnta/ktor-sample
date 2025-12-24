package com.example.data.equipment

import com.example.data.powerunit.PowerUnitEntity
import com.example.plugins.JsonConfig
import org.jetbrains.exposed.v1.dao.with
import plugins.newSuspendTransaction

class EquipmentRepository {

    suspend fun getAll(): List<EquipmentDto> = newSuspendTransaction {
        EquipmentEntity
            .all()
            .with(EquipmentEntity::powerUnit)
            .map { it.toDto() }
    }

    suspend fun getById(id: Int): EquipmentDto? = newSuspendTransaction {
        EquipmentEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: EquipmentDto): EquipmentDto = newSuspendTransaction {
        val entity = EquipmentEntity.new {
            name = dto.name
            shortName = dto.shortName
            powerUnit = dto.powerUnitId?.let { PowerUnitEntity.findById(it) }

            type = dto.type
            customAttributes = dto.customAttributes.let { JsonConfig.json.encodeToString(it) }
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: EquipmentDto): Boolean = newSuspendTransaction {
        val entity = EquipmentEntity.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            powerUnit = dto.powerUnitId?.let { PowerUnitEntity.findById(it) }

            type = dto.type
            customAttributes = dto.customAttributes.let { JsonConfig.json.encodeToString(it) }
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = EquipmentEntity.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }
}