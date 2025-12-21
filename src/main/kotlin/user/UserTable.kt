package com.example.models

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import org.jetbrains.exposed.v1.datetime.datetime
import kotlin.time.Clock

object UserTable : UUIDTable("users") {
    val name = varchar("name", 50)
    val email = varchar("email", 100).uniqueIndex()
    val createdAt = datetime("")
        .clientDefault { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) }
}



