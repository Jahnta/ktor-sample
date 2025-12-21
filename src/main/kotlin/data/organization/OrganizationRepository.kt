package com.example.data.organization

import com.example.data.area.AreaEntity
import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class OrganizationRepository {

    suspend fun getAll(): List<OrganizationWithChildrenDto> = suspendTransaction {
        OrganizationEntity.find { OrganizationTable.parentId.isNull() }.map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): OrganizationWithChildrenDto? = suspendTransaction {
        OrganizationEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: OrganizationCreateDto): OrganizationDto = suspendTransaction {
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

    suspend fun update(id: Int, dto: OrganizationCreateDto): Boolean = suspendTransaction {
        val entity = OrganizationEntity.findById(id) ?: return@suspendTransaction false

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

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = OrganizationEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}