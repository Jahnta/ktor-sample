package organization

import area.AreaEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import powerPlants.PowerPlantEntity
import powerPlants.PowerPlantTable

class OrganizationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrganizationEntity>(OrganizationTable)

    var fullName by OrganizationTable.fullName
    var shortName by OrganizationTable.shortName
    var parent by OrganizationEntity optionalReferencedOn OrganizationTable.parentId

    var area by AreaEntity optionalReferencedOn OrganizationTable.areaId
    var address by OrganizationTable.address
    var email by OrganizationTable.email
    var website by OrganizationTable.website
    var phoneNumber by OrganizationTable.phoneNumber


    fun toDtoWithChildren(): OrganizationWithChildrenDto {
        val organizations = find { OrganizationTable.parentId eq this.id }.map { it.toDtoWithChildren() }
        val plants = PowerPlantEntity.find { PowerPlantTable.parentId eq this.id }.map { it.toDto() }
        return OrganizationWithChildrenDto(
            id = id.value,
            fullName = fullName,
            shortName = shortName,
            parentId = parent?.id?.value,
            organizations = organizations,
            plants = plants,

            area = area?.toDto(),
            address = address,
            email = email,
            website = website,
            phoneNumber = phoneNumber,
        )
    }

    fun toDto() = OrganizationDto(
        id = id.value,
        fullName = fullName,
        shortName = shortName,
        parentId = parent?.id?.value,

        area = area?.toDto(),
        address = address,
        email = email,
        website = website,
        phoneNumber = phoneNumber,
    )
}