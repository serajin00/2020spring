package com.example.infra.db.ts

import com.example.dto.data.common.DateTimeData
import com.example.dto.data.common.TimestampsData
import com.example.infra.db.SqlDateTime
import com.example.infra.db.toDomain
import kotlinx.coroutines.flow.toList
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.flow
import org.springframework.stereotype.Service

data class TableTimestamps(
    val createdAt: SqlDateTime?,
    val updatedAt: SqlDateTime?
) {
    fun toData(): TimestampsData {
        return TimestampsData(
            createdAt = createdAt?.toDomain()?.let { DateTimeData.fromDomain(it) },
            updatedAt = updatedAt?.toDomain()?.let { DateTimeData.fromDomain(it) }
        )
    }
}

interface TableTimestampsQueryService {

    suspend fun <ID> getTimestamps(
        tableName: String, ids: Collection<ID>,
        idColumnName: String = "id",
        createdColumnName: String = "created_at",
        updatedColumnName: String = "updated_at"
    ): Map<ID, TableTimestamps>
}

@Service
internal class TableTimestampsQueryServiceImpl(
    val dbClient: DatabaseClient
) : TableTimestampsQueryService {

    override suspend fun <ID> getTimestamps(
        tableName: String,
        ids: Collection<ID>,
        idColumnName: String,
        createdColumnName: String,
        updatedColumnName: String
    ): Map<ID, TableTimestamps> {
        val tss: Map<String, TableTimestamps> = dbClient.select()
            .from(tableName)
            .project(idColumnName, createdColumnName, updatedColumnName)
            .map { row ->
                val stringId = row.get(0)!!.toString()
                stringId to TableTimestamps(
                    createdAt = row.get(1, SqlDateTime::class.java),
                    updatedAt = row.get(2, SqlDateTime::class.java)
                )
            }
            .flow().toList().toMap()

        return ids
            .mapNotNull { id ->
                val ts = tss[id.toString()]
                ts?.let { id to ts }
            }
            .toMap()
    }
}