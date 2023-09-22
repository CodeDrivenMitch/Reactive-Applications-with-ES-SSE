package io.axoniq.livecoding.sse.api

import org.axonframework.modelling.command.TargetAggregateIdentifier


data class CreateAccountCommand(
    val id: String,
)

data class DepositToAccountCommand(
    @TargetAggregateIdentifier
    val accountId: String,
    val amount: Double,
)

data class WithdrawBalanceFromAccountCommand(
    @TargetAggregateIdentifier
    val accountId: String,
    val amount: Double,
)
