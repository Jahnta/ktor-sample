package com.example.user

import com.example.models.UserTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass
import java.util.UUID

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var name by UserTable.name
    var email by UserTable.email
    var createdAt by UserTable.createdAt

    fun toDto() = UserDto(
        id = id.value.toString(),
        name = name,
        email = email,
        createdAt = createdAt.toString()
    )
}