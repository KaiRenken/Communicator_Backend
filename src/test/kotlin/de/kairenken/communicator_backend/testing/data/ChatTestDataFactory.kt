package de.kairenken.communicator_backend.testing.data

import de.kairenken.communicator_backend.domain.chat.Chat
import java.util.*

class ChatTestDataFactory {

    private var id = UUID.fromString("1108950a-2de9-11ed-a261-0242ac120002")
    private var name = "test-name"

    companion object Factory {
        fun aTestChat() = ChatTestDataFactory()
    }

    fun withId(id: UUID): ChatTestDataFactory {
        this.id = id
        return this
    }

    fun withName(name: String): ChatTestDataFactory {
        this.name = name
        return this
    }

    fun build() = Chat(
        Chat.Id(id),
        Chat.Name(name)
    )
}