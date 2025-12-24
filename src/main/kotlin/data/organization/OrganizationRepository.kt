package com.example.data.organization

import com.example.data.area.AreaEntity
import org.jetbrains.exposed.v1.core.isNull
import plugins.newSuspendTransaction

class OrganizationRepository {

    suspend fun getAll(): List<OrganizationWithChildrenDto> = newSuspendTransaction {
        OrganizationEntity
            .find { OrganizationTable.parentId.isNull() }
            .map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): OrganizationWithChildrenDto? = newSuspendTransaction {
        OrganizationEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: OrganizationCreateDto): OrganizationDto = newSuspendTransaction {
        val entity = OrganizationEntity.new {
            name = dto.name
            shortName = dto.shortName
            parent = dto.parentId?.let { OrganizationEntity.findById(it) }

            area = dto.areaId?.let { AreaEntity.findById(it) }
            address = dto.address
            email = dto.email
            website = dto.website
            phoneNumber = dto.phoneNumber
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: OrganizationCreateDto): Boolean = newSuspendTransaction {
        val entity = OrganizationEntity.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            shortName = dto.shortName
            parent = dto.parentId?.let { OrganizationEntity.findById(it) }

            area = dto.areaId?.let { AreaEntity.findById(it) }
            address = dto.address
            email = dto.email
            website = dto.website
            phoneNumber = dto.phoneNumber
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = OrganizationEntity.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }
}