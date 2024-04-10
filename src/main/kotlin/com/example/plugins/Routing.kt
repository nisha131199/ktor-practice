package com.example.plugins

import com.example.App.gson
import com.example.model.auth.CreateUserRequest
import com.example.model.auth.LoginRequest
import com.example.repository.UserRepository
import com.example.response.ApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        route("/user") {
            post ("/register") {
                val request = gson.fromJson(call.receiveText(), CreateUserRequest::class.java)
//                val request = call.receive(CreateUserRequest::class)
                if (
                    !request.name.isNullOrBlank() &&
                    !request.email.isNullOrBlank() &&
                    !request.phone.isNullOrBlank() &&
                    !request.password.isNullOrBlank()
                ) {
                    val userId = UserRepository.createUser(
                        request.name,
                        request.email,
                        request.phone,
                        request.password
                    )
                    call.respond(
                        HttpStatusCode.Created,
                        "User is created with id: $userId"
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Please enter all details"
                    )
                }
            }

            post ("/login") {
                headers {
                    this["Content-Type"] = "application/json"
                }
                val request = gson.fromJson(call.receiveText(), LoginRequest::class.java)
//                val request = call.receive(LoginRequest::class)
                if (
                    !request.phone.isNullOrBlank() &&
                    !request.password.isNullOrBlank()) {
                    val user = UserRepository.login(
                        request.phone,
                        request.password
                    )
                    if (user != null) {
                        call.respond(
                            HttpStatusCode.OK,
                            gson.toJson(
                                ApiResponse(
                                    error = false,
                                    status = HttpStatusCode.OK.value,
                                    message = "Success",
                                    data = user.getUserData()
                                )
                            )
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound
                        )
                    }
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Please enter all fields"
                    )
                }
            }

            get {
//                val param = call.receiveParameters()
                val userId = call.request.queryParameters["id"]

                if (userId != null) {
                    val user = UserRepository.getUserDetails(userId.toInt())

                    if (user != null) {
                        call.respond(
                            HttpStatusCode.OK,
                            gson.toJson(
                                ApiResponse(
                                    error = false,
                                    status = HttpStatusCode.OK.value,
                                    message = "Success",
                                    data = user.getUserData()
                                )
                            )
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound,
                            "User not found"
                        )
                    }
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Please enter user id"
                    )
                }
            }

            delete {
//                val param = call.receiveParameters()
                val userId = call.request.queryParameters["id"]

                if (userId != null) {
                    val isDeleted = UserRepository.deleteUser(userId.toInt())

                    if (isDeleted) {
                        call.respond(
                            HttpStatusCode.OK,
                            "User deleted successfully"
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound,
                            "User not found"
                        )
                    }
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Please enter user id"
                    )
                }
            }
        }
    }
}
