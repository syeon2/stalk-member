package io.waterkite94.stalk.usecase

import io.waterkite94.stalk.encrypt.util.SecurityUtil
import io.waterkite94.stalk.usecase.port.AuthenticationCodePort
import io.waterkite94.stalk.usecase.port.FollowPersistencePort
import io.waterkite94.stalk.usecase.port.MemberPersistencePort
import io.waterkite94.stalk.usecase.port.SmtpPort
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
    protected lateinit var smtpPort: SmtpPort

    @Mock
    protected lateinit var followPersistencePort: FollowPersistencePort

    @Mock
    protected lateinit var env: Environment
}
