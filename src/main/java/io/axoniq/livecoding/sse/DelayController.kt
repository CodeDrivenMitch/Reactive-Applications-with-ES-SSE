package io.axoniq.livecoding.sse

import io.axoniq.livecoding.sse.query.BalanceProjection
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["api"])
class DelayController {

    @GetMapping(path = ["delay"])
    fun getDelay(): Long {
        return BalanceProjection.DELAY
    }

    @PutMapping(path = ["delay/{amount}"])
    fun setDelay(@PathVariable amount: Long) {
        BalanceProjection.DELAY = amount
    }
}
