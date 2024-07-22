package io.waterkite94.stalk.usecase

import io.waterkite94.stalk.mail.MailUtil
import io.waterkite94.stalk.security.util.SecurityUtil
import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
abstract class IntegrationTestSupport {
    @Mock
    protected lateinit var memberPersistencePort: MemberPersistencePort

    @Mock
    protected lateinit var authenticationCodePort: AuthenticationCodePort

    @Mock
    protected lateinit var securityUtil: SecurityUtil

    @Mock
    protected lateinit var mailUtil: MailUtil

    @Mock
    protected lateinit var env: Environment
}
