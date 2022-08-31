package de.kairenken.communicator_backend.domain.chat

import java.util.*

class Chat(val id: Id = Id(), val name: Name) {

    class Id(val value: UUID = UUID.randomUUID())

    class Name(val value: String) {
        init {
            require(value.isNotBlank()) { "Chat.Name is blank" }
        }
    }
}