package routes

import com.example.data.organization.OrganizationCreateDto
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.data.organization.OrganizationRepository

fun Route.organizationRoutes(repository: OrganizationRepository) {

    route("organizations") {

        get {
            call.respond(HttpStatusCode.OK, repository.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val dto = repository.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound)

            call.respond(HttpStatusCode.OK, dto)
        }

        post {
            val dto = call.receive<OrganizationCreateDto>()
            val created = repository.create(dto)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]!!.toInt()
            val dto = call.receive<OrganizationCreateDto>()

            if (!repository.update(id, dto)) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK)
            }
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toInt()

            if (!repository.delete(id)) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}