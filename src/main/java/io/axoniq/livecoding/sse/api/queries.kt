package io.axoniq.livecoding.sse.api

class GetBalanceOverview


data class BalanceOverview(
    val overview: List<BalanceItem>,
)

data class BalanceItem(
    val accountId: String,
    val balance: Double,
)
