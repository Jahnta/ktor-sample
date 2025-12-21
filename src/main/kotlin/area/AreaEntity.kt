package area

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class AreaEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AreaEntity>(AreaTable)

    var name by AreaTable.name
    var parent by AreaEntity optionalReferencedOn AreaTable.parentId

    fun toDtoWithChildren(): AreaWithChildrenDto {
        val areas = find { AreaTable.parentId eq this.id }.map { it.toDtoWithChildren() }
        return AreaWithChildrenDto(
            id = id.value,
            name = name,
            parentId = parent?.id?.value,
            areas = areas,
        )
    }

    fun toDto() = AreaDto(
        id = id.value,
        name = name,
        parentId = parent?.id?.value,
    )
}