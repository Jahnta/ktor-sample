package area

import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

class AreaRepository {

    suspend fun getAll(): List<AreaWithChildrenDto> = suspendTransaction {
        AreaEntity.find { AreaTable.parentId.isNull() }.map { it.toDtoWithChildren() }
    }

    suspend fun getById(id: Int): AreaWithChildrenDto? = suspendTransaction {
        AreaEntity.findById(id)?.toDtoWithChildren()
    }

    suspend fun create(dto: AreaDto): AreaDto = suspendTransaction {
        val entity = AreaEntity.new {
            name = dto.name
            parent = dto.parentId?.let { AreaEntity.findById(it) }
        }
        entity.toDto()
    }

    suspend fun update(id: Int, dto: AreaDto): Boolean = suspendTransaction {
        val entity = AreaEntity.findById(id) ?: return@suspendTransaction false

        entity.apply {
            name = dto.name
            parent = dto.parentId?.let { AreaEntity.findById(it) }
        }
        true
    }

    suspend fun delete(id: Int): Boolean = suspendTransaction {
        val entity = AreaEntity.findById(id) ?: return@suspendTransaction false
        entity.delete()
        true
    }
}