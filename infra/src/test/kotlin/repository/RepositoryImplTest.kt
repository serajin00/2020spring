package com.example.infra.repository

import com.example.domain.repository.LocationRepository
import com.example.domain.repository.ReservationRepository
import com.example.infra.InfraTest
import com.example.infra.test.support.RollbackTestSupport
import com.example.test.models.TestLocations
import com.example.test.models.TestReservations
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@InfraTest
class RepositoryImplTest : RollbackTestSupport() {

    @Test
    fun locationRepository(
        @Autowired repo: LocationRepository
    ) = withRollback {
        val locations = (1..5).map { i ->
            val location = TestLocations.location.copy(name = "Name $i")
            repo.save(location)
        }

        assertEquals("Name 5", repo.findById(locations.last().id!!)!!.name)

        val page = repo.findAll(pageable = PageRequest.of(0, 10))
        page.content.forEach {
            println(it)
        }
        assertTrue(page.content.any { it.name == "Name 3"} )
    }

    @Test
    fun reservationRepository(
        @Autowired repo: ReservationRepository
    ) = withRollback {
        val reservations = (1..4).map { i ->
            val reservation = TestReservations.reservation.copy(locationId = 1, username = "User $i")
            repo.save(reservation)
        }
        assertTrue("User 3" in repo.findByLocation(1).map { it.username })
        assertEquals(1L ,repo.findByUsername("User 2").map { it.locationId }.distinct().single())
    }
}