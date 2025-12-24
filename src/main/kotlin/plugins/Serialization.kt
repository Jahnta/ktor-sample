package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

object JsonConfig {
    val json = Json {
        prettyPrint = true       // для БД и CSV не нужно
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = false
    }
}

fun Application.configureSerialization() {

    install(ContentNegotiation) {
        json(JsonConfig.json)
    }
}
