package de.kairenken.communicator_backend.testing.data

import de.kairenken.communicator_backend.domain.message.Message
import java.util.*

class MessageTestDataFactory {

    private var id = UUID.fromString("b6ab7aec-3021-11ed-a261-0242ac120002")
    private var chatRefId = UUID.fromString("1108950a-2de9-11ed-a261-0242ac120002")
    private var content = "test-content"

    companion object Factory {
        fun aTestMessage() = MessageTestDataFactory()
    }

    fun withId(id: UUID): MessageTestDataFactory {
        this.id = id
        return this
    }

    fun withChatRefId(chatRefId: UUID): MessageTestDataFactory {
        this.chatRefId = chatRefId
        return this
    }

    fun withContent(content: String): MessageTestDataFactory {
        this.content = content
        return this
    }

    fun build() = Message(
        Message.Id(id),
        Message.ChatRefId(chatRefId),
        Message.Content(content)
    )
}