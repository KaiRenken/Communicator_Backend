package de.kairenken.communicator_backend.domain.chat

import java.util.*

class Chat(val id: Id, val messageIds: List<MessageId> = emptyList()) {

    class Id(val value: UUID = UUID.randomUUID())

    class MessageId(val value: UUID)
}