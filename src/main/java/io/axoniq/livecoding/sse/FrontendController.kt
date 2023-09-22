package io.axoniq.livecoding.sse

import io.axoniq.livecoding.sse.api.CreateAccountCommand
import io.axoniq.livecoding.sse.api.DepositToAccountCommand
import io.axoniq.livecoding.sse.api.GetBalanceOverviewForAccount
import io.axoniq.livecoding.sse.api.WithdrawBalanceFromAccountCommand
import io.axoniq.livecoding.sse.query.BalanceProjection
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.common.IdentifierFactory
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.SubscriptionQueryResult
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping(path = ["api"])
class FrontendController(
    private val queryGateway: QueryGateway,
    private val commandGateway: CommandGateway
) {
    @PostMapping(path = ["account"])
    fun createAccount(): CompletableFuture<String> {
        val accountId = IdentifierFactory.getInstance().generateIdentifier().split("-").first()
        return commandGateway.send(CreateAccountCommand(accountId))
    }

    @PostMapping(path = ["account/{accountId}/withdraw"])
    fun withdrawFromAccount(@PathVariable accountId: String, @RequestBody body: AmountBody): CompletableFuture<Void> {
        return commandGateway.send(WithdrawBalanceFromAccountCommand(accountId, body.amount))
    }

    @PostMapping(path = ["account/{accountId}/deposit"])
    fun depositToAccount(@PathVariable accountId: String, @RequestBody body: AmountBody): CompletableFuture<Void> {
        return commandGateway.send(DepositToAccountCommand(accountId, body.amount))
    }

    @GetMapping(path = ["balance/{accountId}"])
    fun getBalance(@PathVariable accountId: String): Flux<ServerSentEvent<Double>> {
        val query = queryGateway.subscriptionQuery(
                GetBalanceOverviewForAccount(accountId),
                ResponseTypes.instanceOf(Double::class.java),
                ResponseTypes.instanceOf(Double::class.java),
        )

        return query.initialResult()
                .concatWith(query.updates())
                .map {
                    ServerSentEvent.builder(it).build()
                }
                .doFinally {
                    query.cancel()
                }
    }

    data class AmountBody(val amount: Double)
}
