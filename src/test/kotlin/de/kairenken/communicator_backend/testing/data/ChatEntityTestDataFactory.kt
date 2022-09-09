package de.kairenken.communicator_backend.testing.data

import de.kairenken.communicator_backend.infrastructure.chat.database.ChatEntity
import java.util.*

class ChatEntityTestDataFactory {

    private var id = UUID.fromString("1108950a-2de9-11ed-a261-0242ac120002")
    private var name = "test-name"

    companion object Factory {
        fun aTestChatEntity() = ChatEntityTestDataFactory()
    }

    fun withId(id: UUID): ChatEntityTestDataFactory {
        this.id = id
        return this
    }

    fun withName(name: String): ChatEntityTestDataFactory {
        this.name = name
        return this
    }

    fun build() = ChatEntity(
        id,
        name
    )
}