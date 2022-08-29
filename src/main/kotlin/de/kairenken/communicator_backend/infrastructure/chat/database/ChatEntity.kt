package de.kairenken.communicator_backend.infrastructure.chat.database

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "chat")
class ChatEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @Column(name = "name", nullable = false)
    val name: String
)