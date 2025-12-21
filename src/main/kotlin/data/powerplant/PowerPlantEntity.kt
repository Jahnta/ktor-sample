package com.example.data.powerPlants

import com.example.data.area.AreaEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import com.example.data.organization.OrganizationEntity
import com.example.data.powerUnits.PowerUnitEntity
import com.example.data.powerUnits.PowerUnitTable

class PowerPlantEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PowerPlantEntity>(PowerPlantTable)

    var name by PowerPlantTable.name
    var shortName by PowerPlantTable.shortName
    var parent by OrganizationEntity optionalReferencedOn PowerPlantTable.parentId

    var area by AreaEntity optionalReferencedOn PowerPlantTable.areaId
    var address by PowerPlantTable.address
    var email by PowerPlantTable.email
    var website by PowerPlantTable.website
    var phoneNumber by PowerPlantTable.phoneNumber

    var type by PowerPlantTable.type
    var electricalPower by PowerPlantTable.electricalPower
    var thermalPower by PowerPlantTable.thermalPower


    fun toDto() = PowerPlantDto(
        id = id.value,
        name = name,
        shortName = shortName,
        parentId = parent?.id?.value,

        area = area?.toDto(),
        address = address,
        email = email,
        website = website,
        phoneNumber = phoneNumber,

        type = type,
        electricalPower = electricalPower,
        thermalPower = thermalPower,
    )

    fun toCreateDto() = PowerPlantCreateDto(
        id = id.value,
        name = name,
        shortName = shortName,
        parentId = parent?.id?.value,

        areaId = area?.id?.value,
        address = address,
        email = email,
        website = website,
        phoneNumber = phoneNumber,

        type = type,
        electricalPower = electricalPower,
        thermalPower = thermalPower,
    )

    fun toDtoWithChildren(): PowerPlantWithChildrenDto {
        val powerUnits = PowerUnitEntity.find { PowerUnitTable.powerPlantId eq this.id }.map { it.toDto() }
        return PowerPlantWithChildrenDto(
            id = id.value,
            name = name,
            shortName = shortName,
            parentId = parent?.id?.value,

            area = area?.toDto(),
            address = address,
            email = email,
            website = website,
            phoneNumber = phoneNumber,

            type = type,
            electricalPower = electricalPower,
            thermalPower = thermalPower,

            powerUnits = powerUnits
        )
    }
}