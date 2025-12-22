package com.example.data.workscope

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.duration

object WorkscopeTable: IntIdTable("workscope") {
    val name = varchar("name", 200)
    val parentId = reference("parent_id", WorkscopeTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val description = text("description").nullable()

    val defaultDuration = duration("default_duration").nullable()

    val customAttributes = text("custom_attributes").nullable()
}