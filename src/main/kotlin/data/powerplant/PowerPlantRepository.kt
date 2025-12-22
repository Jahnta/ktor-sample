package com.example.data.powerplant

import com.example.data.area.AreaEntity
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import com.example.data.organization.OrganizationEntity

class PowerPlantRepository {

    suspend fun getAll(): List<PowerPlantWithChildrenDto> = suspendTransaction {
        PowerPlantEntity.all().map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): PowerPlantWithChildrenDto? = suspendTransaction {
        PowerPlantEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: PowerPlantCreateDto): PowerPlantCreateDto = suspendTransaction {
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
        entity.toCreateDto()
    }

    suspend fun update(id: Int, dto: PowerPlantCreateDto): Boolean = suspendTransaction {
        val entity = PowerPlantEntity.findById(id) ?: return@suspendTransaction false

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

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = PowerPlantEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}