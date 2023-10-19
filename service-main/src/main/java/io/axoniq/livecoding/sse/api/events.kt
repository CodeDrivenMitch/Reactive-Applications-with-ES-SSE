package io.axoniq.livecoding.sse.api

data class AccountCreatedEvent(
    val id: String,
)

data class BalanceAddedToAccountEvent(
    val accountId: String,
    val amount: Double,
    val balance: Double,
)

data class BalanceWithdrawnFromAccountEvent(
    val accountId: String,
    val amount: Double,
    val balance: Double,
)
