package io.github.endzeitbegins.springequalityoffsetdatetime

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate
import java.time.ZoneId

@ExtendWith(SpringExtension::class)
@WebFluxTest(SomeController::class)
class SpringEqualityOffsetdatetimeApplicationTests {

    @MockkBean
    lateinit var someService: SomeService

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun `create Ok`() {
        val expectedId = 123L

        val zoneId = ZoneId.of("Europe/Berlin")
        val dto = SomeDTO(
            id = null,
            startDate = LocalDate.of(2021, 4, 23)
                .atStartOfDay(zoneId).toOffsetDateTime(),
        )
        val expectedToStore = dto.toEntity()
        val stored = expectedToStore.copy(id = expectedId)

        coEvery { someService.save(any()) } returns stored

        client
            .post()
            .uri("/some/api/new")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dto)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(expectedId)

        coVerify {
            someService.save(expectedToStore)
        }
    }
}
