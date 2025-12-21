package di

import com.example.data.area.AreaRepository
import com.example.data.equipment.EquipmentRepository
import org.koin.dsl.module
import com.example.data.organization.OrganizationRepository
import com.example.data.powerPlants.PowerPlantRepository
import com.example.data.powerUnits.PowerUnitRepository

val appModule = module {
    single { AreaRepository() }
    single { OrganizationRepository() }
    single { PowerPlantRepository() }
    single { PowerUnitRepository() }
    single { EquipmentRepository() }
}