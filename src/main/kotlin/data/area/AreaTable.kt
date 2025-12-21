package com.example.data.area

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

object AreaTable: IntIdTable("areas") {
    val name = varchar("name", 100)
    val parentId = reference("parent_id", AreaTable, onDelete = ReferenceOption.CASCADE).nullable()
}