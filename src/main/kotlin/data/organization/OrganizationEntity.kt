package com.example.data.organization

import com.example.data.area.AreaEntity
import com.example.data.powerplant.PowerPlantEntity
import com.example.data.powerplant.PowerPlantTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class OrganizationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrganizationEntity>(OrganizationTable)

    var name by OrganizationTable.name
    var shortName by OrganizationTable.shortName
    var parent by OrganizationEntity optionalReferencedOn OrganizationTable.parentId

    var area by AreaEntity optionalReferencedOn OrganizationTable.areaId
    var address by OrganizationTable.address
    var email by OrganizationTable.email
    var website by OrganizationTable.website
    var phoneNumber by OrganizationTable.phoneNumber

    val organizations by OrganizationEntity optionalReferrersOn OrganizationTable.parentId
    val powerPlants by PowerPlantEntity optionalReferrersOn PowerPlantTable.parentId


    fun toDtoWithChildren(): OrganizationWithChildrenDto {
        return OrganizationWithChildrenDto(
            id = id.value,
            name = name,
            shortName = shortName,
            parentId = parent?.id?.value,
            organizations = organizations.map { it.toDtoWithChildren() },
            powerPlants = powerPlants.map { it.toDto() },

            area = area?.toDto(),
            address = address,
            email = email,
            website = website,
            phoneNumber = phoneNumber,
        )
    }

    fun toDto() = OrganizationDto(
        id = id.value,
        name = name,
        shortName = shortName,
        parentId = parent?.id?.value,

        area = area?.toDto(),
        address = address,
        email = email,
        website = website,
        phoneNumber = phoneNumber,
    )
}