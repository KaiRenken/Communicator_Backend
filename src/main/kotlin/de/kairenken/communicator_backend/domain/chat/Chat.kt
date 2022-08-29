package de.kairenken.communicator_backend.domain.chat

import java.util.*

class Chat(val id: Id = Id(), val name: Name, val messageIds: List<MessageId> = emptyList()) {

    class Id(val value: UUID = UUID.randomUUID())

    class Name(val value: String) {
        init {
            require(value.isNotBlank()) { "Chat.Name is blank" }
        }
    }

    class MessageId(val value: UUID)
}