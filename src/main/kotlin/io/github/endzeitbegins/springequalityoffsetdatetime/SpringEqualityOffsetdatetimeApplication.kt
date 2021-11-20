package io.github.endzeitbegins.springequalityoffsetdatetime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.time.OffsetDateTime

@SpringBootApplication
class SpringEqualityOffsetdatetimeApplication

fun main(args: Array<String>) {
    runApplication<SpringEqualityOffsetdatetimeApplication>(*args)
}

@RestController
@RequestMapping("/some/api")
class SomeController(
    private val someService: SomeService,
) {
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(@RequestBody dto: SomeDTO): SomeEntity {
        return someService.save(dto.toEntity())
    }
}

@Service
class SomeService {
    fun save(entity: SomeEntity): SomeEntity = TODO("Never invoked in test")
}

data class SomeDTO(
    val id: Long? = null,
    val startDate: OffsetDateTime,
) {

    fun toEntity(
        userId: Long? = null  // this is optional and nullable now - other than in question
    ): SomeEntity {
        return SomeEntity(
            id = id,
            startDate = startDate,
        )
    }
}

data class SomeEntity(
    val id: Long?, // this is nullable now - other than in question
    val startDate: OffsetDateTime,
)