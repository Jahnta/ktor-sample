package routes

import com.example.user.UserDto
import com.example.user.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoutes(repository: UserRepository) {

    route("users") {

        get {
            call.respond(HttpStatusCode.OK, repository.getAll())
        }

        get("/{id}") {
            val id = UUID.fromString(call.parameters["id"]!!)
            val area = repository.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(HttpStatusCode.OK, area)
        }

        post {
            val dto = call.receive<UserDto>()
            val created = repository.create(dto)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = UUID.fromString(call.parameters["id"]!!)
            val dto = call.receive<UserDto>()

            if (!repository.update(id, dto)) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK)
            }
        }

        delete("/{id}") {
            val id = UUID.fromString(call.parameters["id"]!!)

            if (!repository.delete(id)) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}