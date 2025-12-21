package powerPlants

import area.AreaEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import organization.OrganizationEntity
import powerUnits.PowerUnitEntity
import powerUnits.PowerUnitTable

class PowerPlantEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PowerPlantEntity>(PowerPlantTable)

    var fullName by PowerPlantTable.fullName
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
        fullName = fullName,
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
        fullName = fullName,
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
        val powerUnits = PowerUnitEntity.find { PowerUnitTable.plantId eq this.id }.map { it.toDto() }
        return PowerPlantWithChildrenDto(
            id = id.value,
            fullName = fullName,
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