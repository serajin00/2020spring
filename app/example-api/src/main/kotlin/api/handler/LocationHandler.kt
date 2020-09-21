package com.example.api.handler

import com.example.api.error.Error400
import com.example.api.error.Error404
import com.example.api.model.LocationPageResponse
import com.example.api.model.LocationResponse
import com.example.domain.model.Location
import com.example.domain.repository.LocationRepository
import com.example.dto.data.LocationData
import com.example.dto.data.common.TimestampsData
import com.example.infra.db.ts.TableTimestampsQueryService
import com.example.service.LocationService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.*

@Controller
class LocationHandler(
    val service: LocationService,
    val repository: LocationRepository,
    val tsquery: TableTimestampsQueryService
) {
    private suspend fun List<Location>.mapToResponse(): List<LocationResponse> {
        val tss = tsquery.getTimestamps("locations", map { it.id })
        return map { location ->
            val id = location.id!!
            LocationResponse(
                id = id,
                data = LocationData.fromDomain(location),
                ts = tss[id]?.toData()
            )
        }
    }

    private suspend fun Location.toResponse(): LocationResponse {
        return listOf(this).mapToResponse().first()
    }

    private suspend fun Page<Location>.mapToResponse(): Page<LocationResponse> {
        return PageImpl(content.mapToResponse(), pageable, totalElements)
    }

    suspend fun list(request: ServerRequest): ServerResponse {
        val pageParam = request.queryParamOrNull("page") ?: "0"
        val sizeParam = request.queryParamOrNull("size") ?: "20"

        val pageable = PageRequest.of(
            pageParam.toIntOrNull() ?: throw Error400(),
            sizeParam.toIntOrNull() ?: throw Error400()
        )

        val page = repository.findAll(pageable)

        val resBody = LocationPageResponse(
            page = page.mapToResponse()
        )

        return ServerResponse.ok()
            .bodyValueAndAwait(resBody)
    }

    suspend fun get(request: ServerRequest): ServerResponse {
        val idPath = request.pathVariable("id")

        val id = idPath.toLongOrNull() ?: throw Error404()

        val location = repository.findById(id) ?: throw Error404()

        val resBody = location.toResponse()

        return ServerResponse.ok()
            .bodyValueAndAwait(resBody)
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val data = runCatching { request.awaitBody<LocationData>() }.getOrNull() ?: throw Error400()

        val location = service.create(data)

        val resBody = location.toResponse()

        return ServerResponse.created(resBody.uri)
            .bodyValueAndAwait(resBody)
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val idPath = request.pathVariable("id")
        val data = runCatching { request.awaitBody<LocationData>() }.getOrNull() ?: throw Error400()

        val id = idPath.toLongOrNull() ?: throw Error404()

        val location = service.update(id, data) ?: throw Error404()

        val resBody = location.toResponse()

        return ServerResponse.ok()
            .bodyValueAndAwait(resBody)
    }

    suspend fun patch(request: ServerRequest): ServerResponse {
        val idPath = request.pathVariable("id")
        val patch = runCatching { request.awaitBody<JsonNode>() }.getOrNull() ?: throw Error400()

        val id = idPath.toLongOrNull() ?: throw Error404()

        val location = service.patch(id, patch) ?: throw Error404()

        val resBody = location.toResponse()

        return ServerResponse.ok()
            .bodyValueAndAwait(resBody)
    }
}