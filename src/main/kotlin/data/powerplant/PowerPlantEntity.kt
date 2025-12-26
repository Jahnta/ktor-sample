package com.example.data.powerplant

import com.example.data.area.AreaEntity
import com.example.data.organization.OrganizationEntity
import com.example.data.powerunit.PowerUnitEntity
import com.example.data.powerunit.PowerUnitTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

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

    val powerUnits by PowerUnitEntity optionalReferrersOn PowerUnitTable.powerPlantId


    fun toDto() = PowerPlantResponseDto(
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

    fun toDtoWithChildren() = PowerPlantResponseWithChildrenDto(
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

        powerUnits = powerUnits.map { it.toDto() }
    )
}
