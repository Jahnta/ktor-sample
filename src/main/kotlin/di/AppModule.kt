package di

import com.example.data.area.AreaRepository
import com.example.data.equipment.EquipmentRepository
import com.example.data.equipment.EventRepository
import org.koin.dsl.module
import com.example.data.organization.OrganizationRepository
import com.example.data.powerplant.PowerPlantRepository
import com.example.data.powerunit.PowerUnitRepository

val appModule = module {
    single { AreaRepository() }
    single { OrganizationRepository() }
    single { PowerPlantRepository() }
    single { PowerUnitRepository() }
    single { EquipmentRepository() }
    single { EventRepository() }
}