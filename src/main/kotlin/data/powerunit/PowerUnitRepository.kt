package com.example.data.powerunit

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import com.example.data.powerplant.PowerPlantEntity

class PowerUnitRepository {

    suspend fun getAll(): List<PowerUnitWithChildrenDto> = suspendTransaction {
        PowerUnitEntity.Companion.all().map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): PowerUnitWithChildrenDto? = suspendTransaction {
        PowerUnitEntity.Companion.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: PowerUnitDto): PowerUnitDto = suspendTransaction {
        val entity = PowerUnitEntity.Companion.new {
            name = dto.name
            shortName = dto.shortName
            powerPlant = dto.powerPlantId?.let { PowerPlantEntity.findById(it) }

            type = dto.type
            capacity = dto.capacity
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: PowerUnitDto): Boolean = suspendTransaction {
        val entity = PowerUnitEntity.Companion.findById(id) ?: return@suspendTransaction false

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
        val entity = PowerUnitEntity.Companion.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}