package de.kairenken.communicator_backend.testing.data

import de.kairenken.communicator_backend.application.message.dto.MessageCreationDto
import java.util.*

class MessageCreationDtoTestDataFactory {

    private var chatRefId = UUID.fromString("1108950a-2de9-11ed-a261-0242ac120002")
    private var content = "test-content"

    companion object Factory {
        fun aTestMessageCreationDto() = MessageCreationDtoTestDataFactory()
    }

    fun withChatRefId(chatRefId: UUID): MessageCreationDtoTestDataFactory {
        this.chatRefId = chatRefId
        return this
    }

    fun withContent(content: String): MessageCreationDtoTestDataFactory {
        this.content = content
        return this
    }

    fun build() = MessageCreationDto(
        chatRefId,
        content
    )
}