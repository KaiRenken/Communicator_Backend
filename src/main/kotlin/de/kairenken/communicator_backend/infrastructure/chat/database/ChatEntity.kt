package de.kairenken.communicator_backend.infrastructure.chat.database

import de.kairenken.communicator_backend.infrastructure.message.database.MessageEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "chat")
class ChatEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    val messageIds: List<MessageEntity>
)