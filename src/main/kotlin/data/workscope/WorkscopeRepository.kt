package com.example.data.equipment

import com.example.data.event.WorkscopeDto
import com.example.data.workscope.WorkscopeEntity
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class WorkscopeRepository {

    suspend fun getAll(): List<WorkscopeDto> = suspendTransaction {
        WorkscopeEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: Int): WorkscopeDto? = suspendTransaction {
        WorkscopeEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: WorkscopeDto): WorkscopeDto = suspendTransaction {
        val entity = WorkscopeEntity.new {
            name = dto.name
            parent = dto.parentId?.let { WorkscopeEntity.findById(it) }

            defaultDuration = dto.defaultDuration

            customAttributes = dto.customAttributes
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: WorkscopeDto): Boolean = suspendTransaction {
        val entity = WorkscopeEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            name = dto.name
            parent = dto.parentId?.let { WorkscopeEntity.findById(it) }

            defaultDuration = dto.defaultDuration

            customAttributes = dto.customAttributes
        }
        true
    }

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = WorkscopeEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}