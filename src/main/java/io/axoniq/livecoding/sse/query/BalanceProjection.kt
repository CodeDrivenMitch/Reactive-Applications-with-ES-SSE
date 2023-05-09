package io.axoniq.livecoding.sse.query

import io.axoniq.livecoding.sse.api.*
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class BalanceProjection {

    private val balanceMap: MutableMap<String, Double> = ConcurrentHashMap()

    @EventHandler
    fun handle(event: AccountCreatedEvent) {
        balanceMap[event.id] = 0.0
    }

    @EventHandler
    fun handle(event: BalanceWithdrawnFromAccountEvent) {
        balanceMap[event.accountId] = event.balance
    }

    @EventHandler
    fun handle(event: BalanceAddedToAccountEvent) {
        balanceMap[event.accountId] = event.balance
    }

    @QueryHandler
    fun handle(query: GetBalanceOverview): BalanceOverview {
        return BalanceOverview(balanceMap.entries.map { BalanceItem(it.key, it.value) })
    }
}
