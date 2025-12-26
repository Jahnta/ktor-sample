package com.example.data.powerplant

import com.example.data.area.AreaEntity
import com.example.data.organization.OrganizationEntity
import org.jetbrains.exposed.v1.dao.with
import plugins.newSuspendTransaction

class PowerPlantRepository {

    suspend fun getAll(): List<PowerPlantResponseWithChildrenDto> = newSuspendTransaction {
        PowerPlantEntity
            .all()
            .with(PowerPlantEntity::parent)
            .with(PowerPlantEntity::powerUnits)
            .with(PowerPlantEntity::area)
            .map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): PowerPlantResponseWithChildrenDto? = newSuspendTransaction {
        PowerPlantEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: PowerPlantCreateDto): PowerPlantResponseDto = newSuspendTransaction {
        val entity = PowerPlantEntity.new {
            name = dto.name
            shortName = dto.shortName
            parent = dto.parentId?.let { OrganizationEntity.findById(it) }

            area = dto.areaId?.let { AreaEntity.findById(it) }
            address = dto.address
            email = dto.email
            website = dto.website
            phoneNumber = dto.phoneNumber

            type = dto.type
            electricalPower = dto.electricalPower
            thermalPower = dto.thermalPower
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: PowerPlantUpdateDto): Boolean = newSuspendTransaction {
        val entity = PowerPlantEntity.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            parent = dto.parentId?.let { OrganizationEntity.findById(it) }

            area = dto.areaId?.let { AreaEntity.findById(it) }
            address = dto.address
            email = dto.email
            website = dto.website
            phoneNumber = dto.phoneNumber

            type = dto.type
            electricalPower = dto.electricalPower
            thermalPower = dto.thermalPower
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = PowerPlantEntity.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }
}