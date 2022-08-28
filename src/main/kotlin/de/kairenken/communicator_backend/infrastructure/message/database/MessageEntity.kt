package de.kairenken.communicator_backend.infrastructure.message.database

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "message")
class MessageEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @Column(name = "chatRefId", nullable = false)
    val chatRefId: UUID,

    @Column(name = "content", nullable = false)
    val content: String
)

