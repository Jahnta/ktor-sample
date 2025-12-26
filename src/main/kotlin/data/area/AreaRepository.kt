package com.example.data.area

import org.jetbrains.exposed.v1.core.isNull
import plugins.newSuspendTransaction

class AreaRepository {

    suspend fun getAll(): List<AreaResponseWithChildrenDto> = newSuspendTransaction {
        AreaEntity
            .find { AreaTable.parentId.isNull() }
            .map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): AreaResponseWithChildrenDto? = newSuspendTransaction {
        AreaEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: AreaCreateDto): AreaResponseDto = newSuspendTransaction {
        val entity = AreaEntity.new {
            name = dto.name
            parent = dto.parentId?.let { AreaEntity.findById(it) }
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: AreaUpdateDto): Boolean = newSuspendTransaction {
        val entity = AreaEntity.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            parent = dto.parentId?.let { AreaEntity.findById(it) }
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = AreaEntity.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }
}