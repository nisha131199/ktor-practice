package com.example

import com.example.db.DatabaseConfig
import com.example.db.DatabaseConfig.initTable
import com.example.plugins.*
import com.google.gson.Gson
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json


object App {
    val gson = Gson()
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseConfig.init().also {
        initTable()
    }
    configureHTTP()
    configureRouting()

    install(ContentNegotiation) {
        Json {
            prettyPrint = true
            isLenient = true
            encodeDefaults = true
        }
    }
}
