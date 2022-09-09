package de.kairenken.communicator_backend.testing.data

import de.kairenken.communicator_backend.application.chat.dto.ChatCreationDto

class ChatCreationDtoTestDataFactory {

    private var name = "test-name"

    companion object Factory {
        fun aTestChatCreationDto() = ChatCreationDtoTestDataFactory()
    }

    fun withName(name: String): ChatCreationDtoTestDataFactory {
        this.name = name
        return this
    }

    fun build() = ChatCreationDto(
        name
    )
}