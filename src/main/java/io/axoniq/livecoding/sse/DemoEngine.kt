package io.axoniq.livecoding.sse

import io.axoniq.livecoding.sse.api.AddBalanceToAccountCommand
import io.axoniq.livecoding.sse.api.CreateAccountCommand
import io.axoniq.livecoding.sse.api.WithdrawBalanceFromAccountCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.common.IdentifierFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class DemoEngine(
    private val commandGateway: CommandGateway
) {
    private val accountList: MutableList<String> = LinkedList()
    private val random = Random()

    @Scheduled(fixedDelay = 100)
    fun randomAction() {
        while (accountList.size < 10) {
            accountList += commandGateway.sendAndWait<String>(
                CreateAccountCommand(
                    IdentifierFactory.getInstance().generateIdentifier()
                )
            )
        }
        val account = accountList.random()
        val amount = random.nextDouble(1000.0)
        if (random.nextBoolean()) {
            commandGateway.send<Unit>(AddBalanceToAccountCommand(account, amount))
                .exceptionally {
                    println("Was not able to add $amount to $account: ${it.message}")
                }
        } else {
            commandGateway.send<Unit>(WithdrawBalanceFromAccountCommand(account, amount))
                .exceptionally {
                    println("Was not able to withdraw $amount from $account: ${it.message}")
                }
        }
    }
}
