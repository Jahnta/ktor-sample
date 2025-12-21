package com.example.plugins

import area.AreaRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import organization.OrganizationRepository
import powerPlants.PowerPlantRepository
import routes.areaRoutes
import routes.organizationRoutes
import routes.powerPlantRoutes
import java.io.File

fun Application.configureRouting(
    areaRepository: AreaRepository,
    organizationRepository: OrganizationRepository,
    powerPlantRepository: PowerPlantRepository
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
            index = "index.html"
        )

        get("/") {
            call.respondFile(File("src/main/resources/static/index.html"))
        }

        areaRoutes(areaRepository)
        organizationRoutes(organizationRepository)
        powerPlantRoutes(powerPlantRepository)

    }
}
