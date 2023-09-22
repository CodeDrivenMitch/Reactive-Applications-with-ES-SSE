package io.axoniq.livecoding.sse

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(Throwable::class)
    fun handle(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.internalServerError().body(exception.message)
    }
}
