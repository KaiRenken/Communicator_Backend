package de.kairenken.communicator_backend.infrastructure.chat.database

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.springframework.stereotype.Repository

@Repository
class ChatRepositoryImpl(private val chatJpaRepository: ChatJpaRepository) : ChatRepository {

    override fun storeChat(chat: Chat) = chat
        .mapToEntity()
        .save()

    override fun doesChatExist(id: Chat.Id) = chatJpaRepository.existsById(id.value)
    override fun findAllChats() = chatJpaRepository
        .findAll()
        .mapToDomain()

    private fun Chat.mapToEntity() = ChatEntity(id = this.id.value, name = this.name.value)

    private fun ChatEntity.save() {
        chatJpaRepository.save(this)
    }

    private fun List<ChatEntity>.mapToDomain() = this.map {
        Chat(
            id = Chat.Id(it.id),
            name = Chat.Name(it.name)
        )
    }
}
