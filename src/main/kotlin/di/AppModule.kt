package di

import area.AreaRepository
import org.koin.dsl.module
import organization.OrganizationRepository
import powerPlants.PowerPlantRepository

val appModule = module {
    single { AreaRepository() }
    single { OrganizationRepository() }
    single { PowerPlantRepository() }
}