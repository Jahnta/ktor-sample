package com.example

import area.AreaRepository
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import di.appModule
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import organization.OrganizationRepository
import plugins.configureDatabases
import powerPlants.PowerPlantRepository

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    val areaRepository by inject<AreaRepository>()
    val organizationRepository by inject<OrganizationRepository>()
    val powerPlantRepository by inject<PowerPlantRepository>()

    configureSerialization()
    configureDatabases()
    configureRouting(
        areaRepository, 
        organizationRepository,
        powerPlantRepository,
    )
}
