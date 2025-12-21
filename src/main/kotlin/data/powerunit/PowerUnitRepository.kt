package com.example.data.powerUnits

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import com.example.data.powerPlants.PowerPlantEntity

class PowerUnitRepository {

    suspend fun getAll(): List<PowerUnitWithChildrenDto> = suspendTransaction {
        PowerUnitEntity.all().map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): PowerUnitWithChildrenDto? = suspendTransaction {
        PowerUnitEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: PowerUnitDto): PowerUnitDto = suspendTransaction {
        val entity = PowerUnitEntity.new {
            name = dto.name
            shortName = dto.shortName
            powerPlant = dto.powerPlantId?.let { PowerPlantEntity.findById(it) }

            type = dto.type
            capacity = dto.capacity
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: PowerUnitDto): Boolean = suspendTransaction {
        val entity = PowerUnitEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            powerPlant = dto.powerPlantId?.let { PowerPlantEntity.findById(it) }

            type = dto.type
            capacity = dto.capacity
        }
        true
    }

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = PowerUnitEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}