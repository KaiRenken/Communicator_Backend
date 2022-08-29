package de.kairenken.communicator_backend.domain.message

import java.util.*

class Message(val id: Id = Id(), val chatRefId: ChatRefId, val content: Content) {

    class Id(val value: UUID = UUID.randomUUID())

    class ChatRefId(val value: UUID)

    class Content(val value: String) {
        init {
            require(value.isNotBlank()) { "Message.Content is blank" }
        }
    }
}