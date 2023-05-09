package io.axoniq.livecoding.sse

import org.axonframework.config.ConfigurerModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class LiveCodingSseApplication {
    @Bean
    fun configurerModule() = ConfigurerModule {
        it.eventProcessing()
            .usingPooledStreamingEventProcessors { t, u -> u.initialToken { s -> s.createHeadToken() } }
    }
}


fun main(args: Array<String>) {
    runApplication<LiveCodingSseApplication>(*args)
}
