package com.example.data.equipment

import com.example.data.event.WorkscopeResponseDto
import com.example.data.workscope.WorkscopeEntity
import plugins.newSuspendTransaction

class WorkscopeRepository {

    suspend fun getAll(): List<WorkscopeResponseDto> = newSuspendTransaction {
        WorkscopeEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: Int): WorkscopeResponseDto? = newSuspendTransaction {
        WorkscopeEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: WorkscopeResponseDto): WorkscopeResponseDto = newSuspendTransaction {
        val entity = WorkscopeEntity.new {
            name = dto.name
            parent = dto.parentId?.let { WorkscopeEntity.findById(it) }

            defaultDuration = dto.defaultDuration

            customAttributes = dto.customAttributes
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: WorkscopeResponseDto): Boolean = newSuspendTransaction {
        val entity = WorkscopeEntity.findById(id) ?: return@newSuspendTransaction false

        entity.apply {
            name = dto.name
            parent = dto.parentId?.let { WorkscopeEntity.findById(it) }

            defaultDuration = dto.defaultDuration

            customAttributes = dto.customAttributes
        }
        true
    }

    suspend fun delete(id: Int): Boolean = newSuspendTransaction {
        val entity = WorkscopeEntity.findById(id) ?: return@newSuspendTransaction false
        entity.delete()
        true
    }
}