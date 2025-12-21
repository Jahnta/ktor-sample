package com.example

import area.AreaRepository
import plugins.configureDatabases
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.user.UserRepository
import di.appModule
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import organization.OrganizationRepository
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
    val userRepository by inject<UserRepository>()
    val organizationRepository by inject<OrganizationRepository>()
    val powerPlantRepository by inject<PowerPlantRepository>()

    configureSerialization()
    configureDatabases()
    configureRouting(
        areaRepository, 
        userRepository,
        organizationRepository,
        powerPlantRepository,
    )
}
