package di

import area.AreaRepository
import com.example.user.UserRepository
import org.koin.dsl.module
import organization.OrganizationRepository
import powerPlants.PowerPlantRepository

val appModule = module {
    single { AreaRepository() }
    single { UserRepository() }
    single { OrganizationRepository() }
    single { PowerPlantRepository() }
}