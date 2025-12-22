package com.example.data.workscope

import com.example.data.event.EventTable
import com.example.data.event.WorkscopeDto
import com.example.data.workscope.WorkscopeTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class WorkscopeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkscopeEntity>(WorkscopeTable)

    var name by WorkscopeTable.name
    var parent by WorkscopeEntity optionalReferencedOn WorkscopeTable.parentId

    var description by WorkscopeTable.description
    var defaultDuration by WorkscopeTable.defaultDuration

    var customAttributes by EventTable.customAttributes


    fun toDto() = WorkscopeDto(
        id = id.value,
        name = name,
        parentId = parent?.id?.value,

        defaultDuration = defaultDuration,

        customAttributes = customAttributes,
    )
}