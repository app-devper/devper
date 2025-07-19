package com.devper.server.routes

import com.devper.shared.common.ListDataResponse
import com.devper.server.dto.toDto
import com.devper.server.dto.toModel
import com.devper.server.dto.toResponseDto
import com.devper.server.model.User
import com.devper.server.service.UserService
import com.devper.shared.user.UserResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.util.getOrFail
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.toList
import org.bson.BsonObjectId

fun Route.users(service: UserService) {

    val namePath = "users"

    fun User.toResponseDto(): UserResponse = toDto()

    fun UserResponse.requestToModel() = toModel()

    fun requestDtoTypeInfo() = typeInfo<UserResponse>()

    fun listRequestTypeInfo() = typeInfo<ListDataResponse<UserResponse>>()

    fun responseDtoTypeInfo() = requestDtoTypeInfo()

    fun listResponseDtoTypeInfo() = listRequestTypeInfo()

    get(namePath) {
        val allModels = service.findAll().toList()
        val response = ListDataResponse(allModels.map { it.toResponseDto() })
        call.respond(response, listResponseDtoTypeInfo())
    }

    get("${namePath}/{id}") {
        val id = call.parameters.getOrFail("id")
        when (val model = service.findModelByIdOrNull(id)) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(model.toResponseDto(), responseDtoTypeInfo())
        }
    }

    post(namePath) {
        val model = call.receive<UserResponse>(requestDtoTypeInfo()).requestToModel()
        when (val createdId = service.save(model)) {
            null -> call.respond(HttpStatusCode.InternalServerError)
            else -> call.respond(HttpStatusCode.Created, (createdId as BsonObjectId).toResponseDto())
        }
    }

    put(namePath) {
        val model = call.receive<UserResponse>(requestDtoTypeInfo()).requestToModel()
        service.updateOne(model)
        call.respond(HttpStatusCode.Accepted)
    }

    delete(namePath) {
        val id = call.parameters.getOrFail("id")
        val count = service.deleteOneById(id)
        call.respond(count.toResponseDto())
    }
}
