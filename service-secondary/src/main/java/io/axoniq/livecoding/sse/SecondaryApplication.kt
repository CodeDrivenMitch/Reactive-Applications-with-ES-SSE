package io.axoniq.livecoding.sse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class SecondaryApplication {
}


fun main(args: Array<String>) {
    runApplication<SecondaryApplication>(*args)
}
