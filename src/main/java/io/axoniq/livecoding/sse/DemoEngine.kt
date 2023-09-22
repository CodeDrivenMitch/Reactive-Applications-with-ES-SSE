package io.axoniq.livecoding.sse

import io.axoniq.livecoding.sse.api.DepositToAccountCommand
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

    @Scheduled(fixedDelay = 1000)
    fun randomAction() {
        while (accountList.size < 10) {
            val id = IdentifierFactory.getInstance().generateIdentifier().split("-").first()
            println("Generated account $id")
            accountList += commandGateway.sendAndWait<String>(
                CreateAccountCommand(
                        id
                )
            )
        }
        val account = accountList.random()
        val amount = random.nextDouble(1000.0)
        if (random.nextBoolean()) {
            commandGateway.send<Unit>(DepositToAccountCommand(account, amount))
        } else {
            commandGateway.send<Unit>(WithdrawBalanceFromAccountCommand(account, amount))
        }
    }
}
