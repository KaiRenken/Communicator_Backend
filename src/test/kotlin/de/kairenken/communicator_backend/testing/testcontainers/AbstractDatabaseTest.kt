package de.kairenken.communicator_backend.testing.testcontainers

import de.kairenken.communicator_backend.infrastructure.chat.database.ChatJpaRepository
import de.kairenken.communicator_backend.infrastructure.chat.database.ChatRepositoryImpl
import de.kairenken.communicator_backend.infrastructure.message.database.MessageChatRefRepositoryImpl
import de.kairenken.communicator_backend.infrastructure.message.database.MessageJpaRepository
import de.kairenken.communicator_backend.infrastructure.message.database.MessageRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.NEVER)
@DataJpaTest
@ActiveProfiles("database")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [PostgresContextInitializer::class])
@Import(ChatRepositoryImpl::class, MessageChatRefRepositoryImpl::class, MessageRepositoryImpl::class)
abstract class AbstractDatabaseTest {

    @Autowired
    protected lateinit var chatJpaRepository: ChatJpaRepository

    @Autowired
    protected lateinit var messageJpaRepository: MessageJpaRepository

    @Autowired
    protected lateinit var chatRepositoryImpl: ChatRepositoryImpl

    @Autowired
    protected lateinit var messageChatRefRepositoryImpl: MessageChatRefRepositoryImpl

    @Autowired
    protected lateinit var messageRepositoryImpl: MessageRepositoryImpl

    @BeforeEach
    fun setUp() {
        chatJpaRepository.deleteAll()
        messageJpaRepository.deleteAll()
    }
}