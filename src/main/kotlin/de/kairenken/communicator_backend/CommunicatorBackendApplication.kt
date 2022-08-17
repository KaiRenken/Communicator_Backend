package de.kairenken.communicator_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommunicatorBackendApplication

fun main(args: Array<String>) {
    runApplication<CommunicatorBackendApplication>(*args)
}
