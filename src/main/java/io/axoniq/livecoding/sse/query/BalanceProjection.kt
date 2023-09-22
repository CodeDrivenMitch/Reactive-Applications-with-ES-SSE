package io.axoniq.livecoding.sse.query

import io.axoniq.livecoding.sse.api.*
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class BalanceProjection(
        private val queryUpdateEmitter: QueryUpdateEmitter
) {
    private val balanceMap: MutableMap<String, Double> = ConcurrentHashMap()

    @EventHandler
    fun handle(event: AccountCreatedEvent) {
        balanceMap[event.id] = 0.0
    }

    @EventHandler
    fun handle(event: BalanceWithdrawnFromAccountEvent) {
        Thread.sleep(DELAY)
        balanceMap[event.accountId] = event.balance
        queryUpdateEmitter.emit(
                GetBalanceOverviewForAccount::class.java,
                { it.accountId == event.accountId },
                event.balance
        )
    }

    @EventHandler
    fun handle(event: BalanceAddedToAccountEvent) {
        Thread.sleep(DELAY)
        balanceMap[event.accountId] = event.balance
        queryUpdateEmitter.emit(
                GetBalanceOverviewForAccount::class.java,
                { it.accountId == event.accountId },
                event.balance
        )
    }

    @QueryHandler
    fun handle(query: GetBalanceOverview): BalanceOverview {
        return BalanceOverview(balanceMap.entries.map { BalanceItem(it.key, it.value) })
    }

    @QueryHandler
    fun handle(query: GetBalanceOverviewForAccount): Double {
        return balanceMap[query.accountId] ?: 0.0
    }

    companion object {
        public var DELAY = 0L
    }
}
