package de.kairenken.communicator_backend.infrastructure.chat.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatJpaRepository : JpaRepository<ChatEntity, UUID>