package de.kairenken.communicator_backend.infrastructure.message.database

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageRepository
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl(private val messageJpaRepository: MessageJpaRepository) : MessageRepository {

    override fun storeMessage(message: Message) = message
        .mapToEntity()
        .save()

    override fun findAllByChatRefId(chatRefId: Message.ChatRefId) = chatRefId
        .findMessages()
        .mapToDomainList()

    private fun Message.mapToEntity() = MessageEntity(
        this.id.value,
        this.chatRefId.value,
        this.content.value
    )

    private fun MessageEntity.save() {
        messageJpaRepository.save(this)
    }

    private fun Message.ChatRefId.findMessages() = messageJpaRepository.findAllByChatRefId(this.value)

    private fun List<MessageEntity>.mapToDomainList() = this.map {
        Message(
            id = Message.Id(it.id),
            chatRefId = Message.ChatRefId(it.chatRefId),
            content = Message.Content(it.content)
        )
    }
}
