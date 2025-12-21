package com.example.plugins

import com.example.data.area.AreaRepository
import com.example.data.equipment.EquipmentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.data.organization.OrganizationRepository
import com.example.data.powerPlants.PowerPlantRepository
import com.example.data.powerUnits.PowerUnitRepository
import com.example.routes.equipmentRoutes
import com.example.routes.powerUnitRoutes
import routes.areaRoutes
import routes.organizationRoutes
import routes.powerPlantRoutes
import java.io.File

fun Application.configureRouting(
    areaRepository: AreaRepository,
    organizationRepository: OrganizationRepository,
    powerPlantRepository: PowerPlantRepository,
    powerUnitRepository: PowerUnitRepository,
    equipmentRepository: EquipmentRepository
) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {

        staticResources(
            "/",
            "static",
        )

        get("/") {
            call.respondFile(File("src/main/resources/static/index.html"))
        }

        areaRoutes(areaRepository)
        organizationRoutes(organizationRepository)
        powerPlantRoutes(powerPlantRepository)
        powerUnitRoutes(powerUnitRepository)
        equipmentRoutes(equipmentRepository)

    }
}
