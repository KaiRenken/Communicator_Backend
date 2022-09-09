package de.kairenken.communicator_backend.testing.data

import de.kairenken.communicator_backend.infrastructure.message.database.MessageEntity
import java.util.*

class MessageEntityTestDataFactory {

    private var id = UUID.fromString("b6ab7aec-3021-11ed-a261-0242ac120002")
    private var chatRefId = UUID.fromString("1108950a-2de9-11ed-a261-0242ac120002")
    private var content = "test-content"

    companion object Factory {
        fun aTestMessageEntity() = MessageEntityTestDataFactory()
    }

    fun withId(id: UUID): MessageEntityTestDataFactory {
        this.id = id
        return this
    }

    fun withChatRefId(chatRefId: UUID): MessageEntityTestDataFactory {
        this.chatRefId = chatRefId
        return this
    }

    fun withContent(content: String): MessageEntityTestDataFactory {
        this.content = content
        return this
    }

    fun build() = MessageEntity(
        id,
        chatRefId,
        content
    )
}