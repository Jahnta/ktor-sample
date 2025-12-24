package com.example.data.powerunit

import com.example.data.powerplant.PowerPlantEntity
import org.jetbrains.exposed.v1.dao.with
import plugins.newSuspendTransaction

class PowerUnitRepository {

    suspend fun getAll(): List<PowerUnitWithChildrenDto> = newSuspendTransaction {
        PowerUnitEntity
            .all()
            .with(PowerUnitEntity::powerPlant)
            .with(PowerUnitEntity::equipment)
            .map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): PowerUnitWithChildrenDto? = newSuspendTransaction {
        PowerUnitEntity.Companion.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: PowerUnitDto): PowerUnitDto = newSuspendTransaction {
        val entity = PowerUnitEntity.Companion.new {
            name = dto.name
            shortName = dto.shortName
            powerPlant = dto.powerPlantId?.let { PowerPlantEntity.findById(it) }

            type = dto.type
            capacity = dto.capacity
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: PowerUnitDto): Boolean = newSuspendTransaction {
        val entity = PowerUnitEntity.Companion.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            powerPlant = dto.powerPlantId?.let { PowerPlantEntity.findById(it) }

            type = dto.type
            capacity = dto.capacity
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = PowerUnitEntity.Companion.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }
}