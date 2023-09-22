package io.axoniq.livecoding.sse.command

import io.axoniq.livecoding.sse.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Account() {
    @AggregateIdentifier
    private lateinit var id: String
    private var balance = 0.0

    @CommandHandler
    constructor(command: CreateAccountCommand) : this() {
        AggregateLifecycle.apply(AccountCreatedEvent(command.id))
    }

    @CommandHandler
    fun handle(command: WithdrawBalanceFromAccountCommand) {
        if (command.amount < 0) {
            throw IllegalArgumentException("Can not withdraw a negative amount! You rascal.")
        }
        if (command.amount > balance) {
            throw IllegalStateException("Not enough funds to withdraw ${command.amount}! Only have $balance available.")
        }
        AggregateLifecycle.apply(BalanceWithdrawnFromAccountEvent(id, command.amount, balance - command.amount))
    }

    @CommandHandler
    fun handle(command: DepositToAccountCommand) {
        if (command.amount < 0) {
            throw IllegalArgumentException("Can not add a negative amount! You rascal.")
        }
        if (balance + command.amount > 100_000) {
            throw IllegalStateException("Depositing these funds would void your government guarantee. Refusing.")
        }
        AggregateLifecycle.apply(BalanceAddedToAccountEvent(id, command.amount, balance + command.amount))
    }

    @EventSourcingHandler
    fun handle(event: AccountCreatedEvent) {
        id = event.id
    }

    @EventSourcingHandler
    fun handle(event: BalanceWithdrawnFromAccountEvent) {
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: BalanceAddedToAccountEvent) {
        balance = event.balance
    }

}
