package io.axoniq.livecoding.sse.api

class GetBalanceOverview

class GetBalanceOverviewForAccount(
    val accountId: String,
)

data class BalanceOverview(
    val overview: List<BalanceItem>,
)

data class BalanceItem(
    val accountId: String,
    val balance: Double,
)
