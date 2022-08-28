package de.kairenken.communicator_backend.infrastructure.message.database

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MessageRepositoryImpl : MessageRepository {

    private val messageStore = mutableMapOf<UUID, Message>()

    override fun storeMessage(message: Message) {
        messageStore.put(message.id.value, message)
    }
}