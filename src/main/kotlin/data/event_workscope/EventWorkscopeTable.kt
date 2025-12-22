package com.example.data.event_workscope

import com.example.data.event.EventTable
import com.example.data.workscope.WorkscopeTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.datetime.duration

object EventWorkscopeTable : IntIdTable("event_workscope") {
    val eventId = reference("event_id", EventTable, onDelete = ReferenceOption.CASCADE)
    val workscopeId = reference("workscope_id", WorkscopeTable, onDelete = ReferenceOption.CASCADE)

    val duration = duration("duration").nullable()
    val status = varchar("status", 50).nullable()
}