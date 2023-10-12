package io.axoniq.livecoding.sse

import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping(path = ["api"])
class FrontendController {
    @PostMapping(path = ["account"])
    fun createAccount(): CompletableFuture<String> {
        return CompletableFuture.completedFuture("null")
    }

    @PostMapping(path = ["account/{accountId}/withdraw"])
    fun withdrawFromAccount(@PathVariable accountId: String, @RequestBody body: AmountBody): CompletableFuture<Void> {
        return CompletableFuture.completedFuture(null)
    }

    @PostMapping(path = ["account/{accountId}/deposit"])
    fun depositToAccount(@PathVariable accountId: String, @RequestBody body: AmountBody): CompletableFuture<Void> {
        return CompletableFuture.completedFuture(null)
    }

    @GetMapping(path = ["balance/{accountId}"])
    fun getBalance(@PathVariable accountId: String): CompletableFuture<Double> {
        return CompletableFuture.completedFuture(0.0)
    }

    data class AmountBody(val amount: Double)
}
