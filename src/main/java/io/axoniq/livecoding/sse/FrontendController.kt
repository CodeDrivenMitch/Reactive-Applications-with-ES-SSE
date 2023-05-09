package io.axoniq.livecoding.sse

import io.axoniq.livecoding.sse.api.BalanceItem
import io.axoniq.livecoding.sse.api.BalanceOverview
import io.axoniq.livecoding.sse.api.GetBalanceOverview
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@CrossOrigin("*")
class FrontendController(
    private val queryGateway: QueryGateway,
) {
    @GetMapping(path = ["overview"])
    fun getOverview() = queryGateway.query(GetBalanceOverview(), ResponseTypes.instanceOf(BalanceOverview::class.java))

    @GetMapping(path = ["stream"])
    fun getStream(): Flux<ServerSentEvent<BalanceItem>> {
        val query = queryGateway.subscriptionQuery(
            GetBalanceOverview(),
            ResponseTypes.instanceOf(BalanceOverview::class.java),
            ResponseTypes.instanceOf(BalanceItem::class.java)
        )
        return query
            .initialResult()
            .flatMapIterable { it.overview }
            .concatWith(query.updates())
            .map {
                ServerSentEvent.builder<BalanceItem>()
                    .event("message")
                    .data(it)
                    .build()
            }
    }
}
