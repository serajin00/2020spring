package com.example.infra.db.repository

import com.example.domain.model.Location
import com.example.domain.repository.LocationRepository
import kotlinx.coroutines.flow.toList
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.core.*
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Service

@Service
internal class LocationRepositoryImpl(
    val dao: LocationDAO,
    val dbClient: DatabaseClient
) : LocationRepository {
    val entityTemplate by lazy { R2dbcEntityTemplate(dbClient) }

    override suspend fun save(entity: Location): Location {
        return dao.save(entity.toTable()).toDomain()
    }

    override suspend fun findById(id: Long): Location? {
        return dao.findById(id)?.toDomain()
    }

    override suspend fun findAll(pageable: Pageable): Page<Location> {
        val total = entityTemplate
            .select<LocationTable>()
            .awaitCount()

        val content = dbClient
            .select()
            .from<LocationTable>()
            .page(pageable)
            .fetch().flow().toList()
            .map { it.toDomain() }

        return PageImpl(content, pageable, total)
    }
}

@Table("locations")
internal data class LocationTable(
    @Id
    val id: Long?,
    val name: String,
    val address: String
)

internal fun Location.toTable(): LocationTable {
    return LocationTable(
        id = id,
        name = name,
        address = address
    )
}

internal fun LocationTable.toDomain(): Location {
    return Location(
        id = id,
        name = name,
        address = address
    )
}

internal interface LocationDAO : CoroutineCrudRepository<LocationTable, Long> {

}