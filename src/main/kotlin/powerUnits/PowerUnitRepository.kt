package powerUnits

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import powerPlants.PowerPlantEntity

class PowerUnitRepository {

    suspend fun getAll(): List<PowerUnitDto> = suspendTransaction {
        PowerUnitEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: Int): PowerUnitDto? = suspendTransaction {
        PowerUnitEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: PowerUnitDto): PowerUnitDto = suspendTransaction {
        val entity = PowerUnitEntity.new {
            fullName = dto.fullName
            shortName = dto.shortName
            plant = dto.plantId?.let { PowerPlantEntity.findById(it) }

            type = dto.type
            capacity = dto.capacity
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: PowerUnitDto): Boolean = suspendTransaction {
        val entity = PowerUnitEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            fullName = dto.fullName
            shortName = dto.shortName
            plant = dto.plantId?.let { PowerPlantEntity.findById(it) }

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