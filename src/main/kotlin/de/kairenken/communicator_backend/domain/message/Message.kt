package de.kairenken.communicator_backend.domain.message

import java.util.*

class Message(val id: Id, val content: Content) {

    class Id(val value: UUID = UUID.randomUUID())

    class Content(val value: String) {
        init {
            require(value.isNotBlank())
        }
    }
}