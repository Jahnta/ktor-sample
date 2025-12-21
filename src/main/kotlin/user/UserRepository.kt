package com.example.user

import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import java.util.*

class UserRepository {

    suspend fun getAll(): List<UserDto> = suspendTransaction {
        UserEntity.all().map { it.toDto() }
    }

    suspend fun getById(id: UUID): UserDto? = suspendTransaction {
        UserEntity.findById(id)?.toDto()
    }

    suspend fun create(dto: UserDto): UserDto = suspendTransaction {
        val entity = UserEntity.new {
            name = dto.name
            email = dto.email
        }
        entity.toDto()
    }

    suspend fun update(id: UUID, dto: UserDto): Boolean = suspendTransaction {
        val entity = UserEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            name = dto.name
            email = dto.email
        }
        true
    }

    suspend fun delete(id: UUID): Boolean = suspendTransaction {
        UserEntity.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }
}