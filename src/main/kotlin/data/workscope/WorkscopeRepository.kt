package com.example.data.equipment

import com.example.data.event.WorkscopeDto
import com.example.data.workscope.WorkscopeEntity
import plugins.newSuspendTransaction

class WorkscopeRepository {

    suspend fun getAll(): List<WorkscopeDto> = newSuspendTransaction {
        WorkscopeEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: Int): WorkscopeDto? = newSuspendTransaction {
        WorkscopeEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: WorkscopeDto): WorkscopeDto = newSuspendTransaction {
        val entity = WorkscopeEntity.new {
            name = dto.name
            parent = dto.parentId?.let { WorkscopeEntity.findById(it) }

            defaultDuration = dto.defaultDuration

            customAttributes = dto.customAttributes
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: WorkscopeDto): Boolean = newSuspendTransaction {
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